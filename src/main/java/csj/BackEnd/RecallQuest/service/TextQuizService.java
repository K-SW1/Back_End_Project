package csj.BackEnd.RecallQuest.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import csj.BackEnd.RecallQuest.domain.TextQuiz;
import csj.BackEnd.RecallQuest.domain.TextChoice;
import csj.BackEnd.RecallQuest.repository.TextQuizRepository;
import csj.BackEnd.RecallQuest.repository.TextChoiceRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


@Service
@RequiredArgsConstructor
public class TextQuizService {

    @Autowired
    private final TextQuizRepository textQuizRepository;

    @Autowired
    private final TextChoiceRepository textChoiceRepository;

//조회
    // [TextQuiz]조회 서비스
    public List<TextQuiz> getAllTextQuizzes() {
        return textQuizRepository.findAll();
    }
    
    // [TextQuiz](선택지)(정답) 조회 서비스
    public List<Map<String, Object>> getTextChoicesByQuizId(int textQuizId) {
        // 텍스트 퀴즈 ID에 해당하는 선택지를 데이터베이스에서 조회하고 퀴즈 ID에 해당하는 선택지들을 찾음.
        List<TextChoice> textChoices = textChoiceRepository.findByTextQuiz_TextQuizId(textQuizId);
        // 조회된 선택지를 맵 형식으로 변환함. convertToMaps()에서 맵 형식으로 변환함.
        return convertToMaps(textChoices);
    }
    // TextChoice 엔티티를 Map의 name:string, value:object 키와 값의 쌍으로 이루어진 구조인 맵 형식으로 맵 리스트로 변환함.
    private List<Map<String, Object>> convertToMaps(List<TextChoice> textChoices) {
        // 변환한 맵들을 담을 리스트를 생성합니다.
        List<Map<String, Object>> maps = new ArrayList<>();

        // 선택지 리스트를 순회하면서 각 선택지를 맵 형식으로 변환합니다.
        for (TextChoice textChoice : textChoices) {
            // 선택지의 정보를 담을 새로운 맵을 생성합니다.
            Map<String, Object> map = new HashMap<>();

            // 맵에 선택지의 "choiceText"와 "answer" 값을 추가합니다.
            map.put("choiceText", textChoice.getChoiceText());
            map.put("answer", textChoice.isAnswer());

            // 변환된 맵을 리스트에 추가합니다.
            maps.add(map);
        }

        // 모든 선택지에 대한 맵을 담은 리스트를 반환합니다.
        return maps;
    }


//추가    
    // [TextQuiz]추가 서비스
    public TextQuiz addTextQuiz(TextQuiz textQuiz) {
        return textQuizRepository.save(textQuiz);
    }

    // [TextQuiz](선택지)(정답) 추가 서비스
    public List<TextChoice> addTextChoicesToQuiz(int textQuizId, List<TextChoice> choices) {
        TextQuiz textQuiz = textQuizRepository.findById(textQuizId) //textQuizId로 데이터베이스에서 TextQuiz 객체를 찾아옴.
                .orElseThrow(() -> new RuntimeException("TextQuiz not found")); //TextQuiz가 없다면 RuntimeException을 발생.

        //choices 선택지 리스트안에 저장 된 값에 대해 반복하면서 각 선택지의 TextQuiz를 설정.
        for (TextChoice choice : choices) {
            choice.setTextQuiz(textQuiz);
        }

        return textChoiceRepository.saveAll(choices);
    }


//삭제
    // [TextQuiz][TextChoice] 삭제 서비스 //[TextChoice]먼저 삭제 후 [TextQuiz] 삭제 되는 방식. 외래키 제약 조건
    @Transactional
    public void deleteTextQuiz(int textQuizId) {
        TextQuiz textQuiz = textQuizRepository.findById(textQuizId)
            .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 텍스트 퀴즈와 관련된 선택지들을 먼저 삭제합니다.
        textChoiceRepository.deleteByTextQuiz(textQuiz);

        // 이후 텍스트 퀴즈를 삭제합니다.
        textQuizRepository.delete(textQuiz);
    }

//수정
    // [TextQuiz] 수정 서비스
    public TextQuiz updateTextQuiz(int textQuizId, TextQuiz updatedTextQuiz) {
        TextQuiz existingTextQuiz = textQuizRepository.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 기존 텍스트 퀴즈의 내용을 업데이트합니다.
        existingTextQuiz.setQuestion(updatedTextQuiz.getQuestion());
        existingTextQuiz.setHint(updatedTextQuiz.getHint());

        // 업데이트된 텍스트 퀴즈를 저장하고 반환합니다.
        return textQuizRepository.save(existingTextQuiz);
    }


    // [TextQuiz] 선택지 수정 서비스
    @Transactional
    public List<TextChoice> updateTextChoices(int textQuizId, List<TextChoice> updatedChoices) {
        // 텍스트 퀴즈 ID로 해당하는 텍스트 퀴즈를 찾습니다.
        TextQuiz textQuiz = textQuizRepository.findById(textQuizId)
                .orElseThrow(() -> new RuntimeException("TextQuiz not found"));

        // 기존의 선택지를 모두 삭제합니다.
        List<TextChoice> existingChoices = textChoiceRepository.findByTextQuiz_TextQuizId(textQuizId);
        textChoiceRepository.deleteAll(existingChoices);

        // 새로운 선택지들을 텍스트 퀴즈에 추가합니다.
        for (TextChoice choice : updatedChoices) {
            choice.setTextQuiz(textQuiz);
        }
        List<TextChoice> savedChoices = textChoiceRepository.saveAll(updatedChoices);

        // 수정된 선택지들을 반환합니다.
        return savedChoices;
    }
}

