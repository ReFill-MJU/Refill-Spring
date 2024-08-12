package site.re_fill.childcare.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import site.re_fill.childcare.dto.GptMessage;
import site.re_fill.childcare.dto.GptResponse;
import site.re_fill.childcare.dto.request.Question;
import site.re_fill.childcare.dto.response.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static site.re_fill.childcare.constant.ChildcareConstant.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChildcareServiceImpl implements ChildcareService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value(value = "${GPT_API_KEY}")
    private String apiKey;

    @Value(value = "${GPT_URL}")
    private String url;

    @Override
    public Answer interpretQuestion(final Question request) {
        List<GptMessage> messages = createGptMessages(request);

        HashMap<String, Object> requestBody = createRequestBody(messages);

        GptResponse chatGptRes = getResponse(createHttpEntity(requestBody));

        String response = chatGptRes.choices().get(0).message().content();

        return Answer.of(response);
    }

    // GPT 에 요청할 메시지를 만드는 메서드
    private static List<GptMessage> createGptMessages(final Question request) {
        List<GptMessage> messages = new ArrayList<>();

        // gpt 역할(프롬프트) 설정
        messages.add(GptMessage.of(SYSTEM, PROMPT));

        // 실제 요청
        messages.add(GptMessage.of(USER, request.data()));

        return messages;
    }

    // GPT 에 요청할 파라미터를 만드는 메서드
    private static HashMap<String, Object> createRequestBody(final List<GptMessage> messages) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", CHAT_MODEL);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", MAX_TOKEN);
        requestBody.put("temperature", TEMPERATURE);
        return requestBody;
    }

    // api 호출에 필요한 Http Header를 만들고 HTTP 객체를 만드는 메서드
    private HttpEntity<HashMap<String, Object>> createHttpEntity(final HashMap<String, Object> chatGptRequest) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(MEDIA_TYPE));
        httpHeaders.add(AUTHORIZATION, apiKey);
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
