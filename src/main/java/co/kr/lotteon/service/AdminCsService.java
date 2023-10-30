package co.kr.lotteon.service;


import co.kr.lotteon.dto.LtCsFaqDTO;
import co.kr.lotteon.dto.LtCsNoticeDTO;
import co.kr.lotteon.dto.LtCsQnaDTO;
import co.kr.lotteon.dto.LtMemberDTO;
import co.kr.lotteon.dto.cspage.CsPageRequestDTO;
import co.kr.lotteon.dto.cspage.CsPageResponseDTO;
import co.kr.lotteon.entity.*;
//import co.kr.lotteon.mapper.LtCsNoticeMapper;
import co.kr.lotteon.mapper.admin.LtAdminCsMapper;
import co.kr.lotteon.repository.LtCsFaqRepository;
import co.kr.lotteon.repository.LtCsNoticeRepository;
import co.kr.lotteon.repository.LtCsQnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCsService {

    @Autowired
    LtCsNoticeRepository ltCsNoticeRepository;

    @Autowired
    LtCsFaqRepository ltCsFaqRepository;

    @Autowired
    LtCsQnaRepository ltCsQnaRepository;

    @Autowired
    LtAdminCsMapper ltAdminCsMapper;

    public void saveNotice(LtCsNoticeDTO dto){

        // DTO를 Entity로 변환
        LtCsNoticeEntity entity = dto.toEntity();

        // DB insert
        ltCsNoticeRepository.save(entity);
    }

    public List<LtCsNoticeDTO> noticeList(){

        return ltCsNoticeRepository.findAll()
                .stream()
                .map(
                        LtCsNoticeEntity::toDTO
                )
                .collect(Collectors.toList());
    }

    public List<LtCsQnaDTO> qnaList() {

        return ltCsQnaRepository.findAll()
                .stream()
                .map(
                        LtCsQnaEntity::toDTO
                )
                .collect(Collectors.toList());

    }


    public CsPageResponseDTO noticeList(CsPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("noticeNo");
        Page<LtCsNoticeEntity> result = null;
        if(pageRequestDTO.getCate1() == 0){
           if(pageRequestDTO.getSearch() == ""){
               result = ltCsNoticeRepository.findAll(pageable);
           } else {
               result = ltCsNoticeRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
           }
        } else{
            if(pageRequestDTO.getSearch().equals("")) {
                result = ltCsNoticeRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            } else{
                result = ltCsNoticeRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<LtCsNoticeDTO> dtoList = result.getContent()
                .stream()
                .map(
                        LtCsNoticeEntity::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return CsPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .noticeList(dtoList)
                .total(totalElement)
                .build();
    }

    // Faq List
    public CsPageResponseDTO faqList(CsPageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("faqNo");
        Page<LtCsFaqEntity> result = null;
        if(pageRequestDTO.getCate1() == 0) {
            if(pageRequestDTO.getSearch() == "") {
                result = ltCsFaqRepository.findAll(pageable);
            } else {
                result = ltCsFaqRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        } else {
            if (pageRequestDTO.getSearch().equals("")) {
                result = ltCsFaqRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            } else {
                result = ltCsFaqRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<LtCsFaqDTO> dtoList = result.getContent()
                .stream()
                .map(LtCsFaqEntity::toDTO)
                .toList();
        int totalElement = (int) result.getTotalElements();
        return CsPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .faqList(dtoList)
                .total(totalElement)
                .build();
    }

    // Qna List
    public CsPageResponseDTO qnaList(CsPageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable("qnaNo");
        Page<LtCsQnaEntity> result = null;
        if(pageRequestDTO.getCate1() == 0){
            if(pageRequestDTO.getSearch() == ""){
                result = ltCsQnaRepository.findAll(pageable);
            } else {
                result = ltCsQnaRepository.findByTitleContains(pageRequestDTO.getSearch(), pageable);
            }
        } else {
            if(pageRequestDTO.getSearch().equals("")) {
                result = ltCsQnaRepository.findByCate1(pageRequestDTO.getCate1(), pageable);
            } else {
                result = ltCsQnaRepository.findByCate1AndTitleContains(pageRequestDTO.getCate1(), pageRequestDTO.getSearch(), pageable);
            }
        }
        List<LtCsQnaDTO> dtoList = result.getContent()
                .stream()
                .map(
                        LtCsQnaEntity::toDTO
                )
                .toList();
        int totalElement = (int) result.getTotalElements();
        return CsPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .qnaList(dtoList)
                .total(totalElement)
                .build();
    }


    // admin_Cs_notice_delete
    public void deleteNotice(int noticeNo) {
        ltCsNoticeRepository.deleteById(noticeNo);
    }
    // admin_Cs_notice_update
    public void updateAdminNotice(LtCsNoticeDTO dto) {

        System.out.println("service : " + dto);

        ltAdminCsMapper.updateAdminNotice(dto);

    }

}