package co.kr.lotteon.service;

import co.kr.lotteon.dto.*;
import co.kr.lotteon.entity.LtCsQnaEntity;
import co.kr.lotteon.mapper.cs.*;
import co.kr.lotteon.repository.LtCsQnaRepository;
import co.kr.lotteon.mapper.my.LtMyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Log4j2
@Service
public class LtCsService {



    private final LtCsIndexMapper ltCsIndexMapper;

    private final LtCsCateMapper ltCsCateMapper;

    private final LtCsQnaMapper ltCsQnaMapper;

    private final LtCsNoticeMapper ltCsNoticeMapper;

    private final LtCsFaqMapper ltCsFaqMapper;

    private final LtCsQnaRepository qnaRepository;

    private final LtMyMapper ltMyMapper;


    public List<LtCsNoticeDTO> selectCsNotices(){
        return ltCsIndexMapper.selectCsNotices();
    }

    public List<LtCsQnaDTO> selectCsQna(){
        return ltCsIndexMapper.selectCsQna();
    }

    public List<LtCsCate1DTO> selectCsCate1(){
        return ltCsCateMapper.selectCsCate1();
    }

    public List<LtCsCate2DTO> selectCsCate2(int cate1){
        return ltCsCateMapper.selectCsCate2(cate1);
    }

    //파일 등록
    public void insertQnaWrite(LtCsQnaDTO dto){

        log.info("insertQnaWrite-----------"+dto);




        if (dto.getMFile1() != null && !dto.getMFile1().isEmpty()) {
            List<String> saveNames = fileUpload(dto);
            // 파일을 선택한 경우에만 처리
            dto.setFile1(saveNames.get(0));
            dto.setFile2(saveNames.get(1));
        }



        ltCsQnaMapper.insertQnaWrite(dto);


    }
    //파일이 저장 될 경로
    @Value("${upload.path.files}")
    private String filePath;

    //파일 업로드
    public List<String> fileUpload(LtCsQnaDTO dto){

        //파일 첨부 경로(절대 경로로 변환)
        String path = new File(filePath).getAbsolutePath();
        //첨부파일 리스트화
        List<MultipartFile> files = Arrays.asList(
                dto.getMFile1()

        );

        //저장된 파일명 리스트 초기화
        List<String> saveNames =new ArrayList<>();
        for (MultipartFile file:files){
            //파일명 변경
            String oName = file.getOriginalFilename(); //업로드할 때 원래 파일 이름
            String ext = oName.substring(oName.lastIndexOf(".")); //확장자
            String sName = UUID.randomUUID().toString() + ext; //새로운 파일 이름 생성
            saveNames.add(sName);
            saveNames.add(oName);
            try {
                file.transferTo(new File(path, sName)); // 저장할 폴더 이름, 저장할 파일 이름

            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return saveNames;
    }

    // notice,qna List Mybatis 로 페이징 만들기

    // 현재 페이지 번호
    public int getCurrentPage(String pg) {
        int currentPage = 1;

        if(pg != null){
            currentPage = Integer.parseInt(pg);
        }

        return currentPage;
    }

    // Notice게시판 총 갯수 카운트
    public int selectCsNoticeTotal() {
        return ltCsNoticeMapper.selectCsNoticeTotal();
    }
    // Notice게시판 cate 참고한 총 갯수 카운트
    public int selectCsNoticeTotalCate(int cate1){
        return ltCsNoticeMapper.selectCsNoticeTotalCate(cate1);
    }

    //myqna 게시판 총 갯수 카운트

    public String selectMyQnaTotal(String writer) {
        return ltMyMapper.selectMyQnaTotal(writer);
    }

    // Qna게시판 총 갯수 카운트
    public int selectCsQnaTotal() {
        return ltCsQnaMapper.selectCsQnaTotal();
    }

    public int selectCsQnaTotalCate(int cate1){
        return ltCsQnaMapper.selectCsQnaTotalCate(cate1);
    }

    // 페이지 마지막 번호
    public int getLastPageNum(int total) {

        int lastPageNum = 0;

        if(total % 10 == 0){
            lastPageNum = total / 10;
        }else{
            lastPageNum = total / 10 + 1;
        }
        log.info("lastPageNum--------"+lastPageNum);
        return lastPageNum;

    }

    // 페이지 그룹
    public int[] getPageGroupNum(int currentPage, int lastPageNum) {
        int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
        int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
        int pageGroupEnd = currentPageGroup * 10;

        if(pageGroupEnd > lastPageNum){
            pageGroupEnd = lastPageNum;
        }

        int[] result = {pageGroupStart, pageGroupEnd};

        return result;
    }

    // 페이지 시작번호
    public int getPageStartNum(int total, int currentPage) {
        int start = (currentPage - 1) * 10;
        return total - start;
    }

    // Limit 시작번호
    public int getStartNum(int currentPage) {
        return (currentPage - 1) * 10;
    }

    //noticeList 전체
    public List<LtCsNoticeDTO> selectCsNoticeListAll(int start){
        return ltCsNoticeMapper.selectCsNoticeListAll(start);
    }
    //noticeList cate 참조
    public List<LtCsNoticeDTO> selectCsNoticeListCate(int cate1, int start){
        return ltCsNoticeMapper.selectCsNoticeListCate(cate1, start);
    }

    public List<LtCsQnaDTO> selectCsQnaListAll(int start){
        return ltCsQnaMapper.selectCsQnaListAll(start);
    }

    public List<LtCsQnaDTO> selectCsQnaListCate(int cate1, int start){
        return ltCsQnaMapper.selectCsQnaListCate(cate1,start);
    }

    //myqna writer 참조

    public List<LtCsQnaDTO> selectMyQnaBoard(String writer , int start) {
        return ltMyMapper.selectMyQnaBoard(writer, start);
    }


    // 게시판 view

    public LtCsNoticeDTO selectCSNoticeView(int noticeNo){
        return ltCsNoticeMapper.selectCSNoticeView(noticeNo);
    }

    public LtCsQnaDTO selectCsQnaView(int qnaNo){
        return ltCsQnaMapper.selectCsQnaView(qnaNo);
    }

    public LtCsFaqDTO selectCsFaqView(int faqNo){
        return ltCsFaqMapper.selectCsFaqView(faqNo);
    }


    //FAQ list

    public List<LtCsFaqDTO> selectCsFaqList10(int cate1){
        return  ltCsFaqMapper.selectCsFaqList10(cate1);
    }

    // Admin Qna View
    public LtCsQnaDTO selectCsAdminQnaView(int qnaNo) {
        return ltCsQnaMapper.selectCsAdminQnaView(qnaNo);
    }

    // Admin Qna View Comment
    public List<LtCsQnaEntity> selectComments(int parent) {
        return qnaRepository.findByParent(parent);
    }
    //Qna modify

    public LtCsQnaDTO selectCsQnaBoard (int qnaNo) {
        return ltCsQnaMapper.selectCsQnaBoard(qnaNo);

    }

    public void updateQnaBoard(LtCsQnaDTO dto){
        ltCsQnaMapper.updateQnaBoard(dto);
    }

    //delete
    public void deleteQnaBoard(int qnaNo){
        ltCsQnaMapper.deleteQnaBoard(qnaNo);
    }

    //qna 답변
    public LtCsQnaDTO selectCsQnaChildBoard(int qnaNo){
        return ltCsQnaMapper.selectCsQnaChildBoard(qnaNo);
    }

    //myqna 답변 board
    public List<LtCsQnaDTO> selectCsQnaComment(int qnaNo){
        return ltMyMapper.selectCsQnaComment(qnaNo);
    }

}

