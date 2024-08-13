package site.re_fill.childcare.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import site.re_fill.child.application.ChildModuleService;
import site.re_fill.child.domain.Child;
import site.re_fill.childcare.constant.GptConstant;
import site.re_fill.childcare.constant.NaverClovaConstant;
import site.re_fill.childcare.dto.GptMessage;
import site.re_fill.childcare.dto.GptResponse;
import site.re_fill.childcare.dto.request.Question;
import site.re_fill.childcare.dto.response.Answer;
import site.re_fill.childcare.dto.response.SummaryAnswer;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChildcareServiceImpl implements ChildcareService {

    private final ChildModuleService childModuleService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${GPT_API_KEY}")
    private String apiKey;

    @Value(value = "${GPT_URL}")
    private String url;

    @Value(value = "${NAVER_CLOVA_ID}")
    private String clientId;

    @Value(value = "${NAVER_CLOVA_SECRET}")
    private String clientSecret;

    @Override
    public Answer interpretQuestion(final Long childId, final Question request) {
        Child child = childModuleService.findChildById(childId);

        List<GptMessage> messages = createGptMessages(child, request);
        HashMap<String, Object> requestBody = createRequestBody(messages);
        GptResponse chatGptRes = getResponse(createHttpEntity(requestBody));
        String response = chatGptRes.choices().get(0).message().content();
        return Answer.of(response);
    }

    @Override
    public SummaryAnswer interpretPersonality(final Long childId) {
        Child child = childModuleService.findChildById(childId);
        Question request = Question.of(child.getAnswer1(), child.getAnswer2(), child.getAnswer3());

        List<GptMessage> messages = createPersonalityGptMessages(request);
        HashMap<String, Object> requestBody = createRequestBody(messages);
        GptResponse chatGptRes = getResponse(createHttpEntity(requestBody));
        String response = chatGptRes.choices().get(0).message().content();
        return SummaryAnswer.from(response);
    }

    private byte[] convertTextToSpeech(final String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(NaverClovaConstant.MEDIA_TYPE));
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", clientId);
        httpHeaders.add("X-NCP-APIGW-API-KEY", clientSecret);

        // 파라미터 설정
        Map<String, String> params = new HashMap<>();
        params.put("speaker", "nara");
        params.put("text", URLEncoder.encode(text, StandardCharsets.UTF_8));
        params.put("volume", "0");
        params.put("speed", "0");
        params.put("pitch", "0");
        params.put("format", "mp3");

        // 요청 본문에 파라미터 추가
        StringBuilder requestBody = new StringBuilder();
        params.forEach((key, value) -> {
            if (!requestBody.isEmpty()) {
                requestBody.append("&");
            }
            requestBody.append(key).append("=").append(value);
        });

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), httpHeaders);

        // 요청 전송
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts"; // TTS API의 URL로 변경
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);

        // 응답 처리
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("TTS 변환 실패: " + response.getStatusCode());
        }
    }

    private static List<GptMessage> createPersonalityGptMessages(final Question request) {
        List<GptMessage> messages = new ArrayList<>();

        // gpt 역할(프롬프트) 설정
        messages.add(GptMessage.of(GptConstant.SYSTEM, GptConstant.PROMPT_PERSONALITY_SUMMARY));

        // 실제 요청
        messages.add(GptMessage.of(GptConstant.USER, request.data()));


        return messages;
    }


    // GPT 에 요청할 메시지를 만드는 메서드
    private static List<GptMessage> createGptMessages(final Child child, final Question request) {
        List<GptMessage> messages = new ArrayList<>();

        // gpt 역할(프롬프트) 설정
        messages.add(GptMessage.of(GptConstant.SYSTEM,
                GptConstant.PROMPT_FRONT +
                        "성격:" + child.getAnswer1() +
                        "알레르기 질환:" + child.getAnswer2() +
                        "좋아하는 것과 싫어하는 것:" + child.getAnswer3() +
                        GptConstant.PROMPT_MIDDLE +
                        child.getName() +
                        GptConstant.PROMPT_BACK));

        // 실제 요청
        messages.add(GptMessage.of(GptConstant.USER, request.data()));


        return messages;
    }

    // GPT 에 요청할 파라미터를 만드는 메서드
    private static HashMap<String, Object> createRequestBody(final List<GptMessage> messages) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", GptConstant.CHAT_MODEL);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", GptConstant.MAX_TOKEN);
        requestBody.put("temperature", GptConstant.TEMPERATURE);
        return requestBody;
    }

    // api 호출에 필요한 Http Header를 만들고 HTTP 객체를 만드는 메서드
    private HttpEntity<HashMap<String, Object>> createHttpEntity(final HashMap<String, Object> chatGptRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(GptConstant.MEDIA_TYPE));
        httpHeaders.add(GptConstant.AUTHORIZATION, apiKey);
        return new HttpEntity<>(chatGptRequest, httpHeaders);
    }

    // GPT API 요청후 response body를 받아오는 메서드
    private GptResponse getResponse(final HttpEntity<HashMap<String, Object>> httpEntity) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        // 답변이 길어질 경우 TimeOut Error 발생하므로 time 설정
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);   //  1min

        restTemplate.setRequestFactory(requestFactory);
        ResponseEntity<GptResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                GptResponse.class);

        return responseEntity.getBody();
    }
}
