package co.kr.lotteon.service;

import co.kr.lotteon.dto.LtProductCartDTO;
import co.kr.lotteon.dto.LtProductDTO;
import co.kr.lotteon.dto.LtProductOrderDTO;
import co.kr.lotteon.dto.LtProductOrderItemDTO;
import co.kr.lotteon.dto.order.CompletePage;
import co.kr.lotteon.dto.order.OrderTotalDTO;
import co.kr.lotteon.entity.*;
import co.kr.lotteon.repository.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductOrderService {
    private final LtProductRepository ltProductRepository;
    private final LtProductCartRepository ltProductCartRepository;
    private final LtProductOrderRepository ltProductOrderRepository;
    private final LtProductOrderItemRepository ltProductOrderItemRepository;
    private final LtMemberRepository ltMemberRepository;
    private final LtMemberPointRepository ltMemberPointRepository;
    private final ModelMapper modelMapper;

    public int getPoint(String uid){
        return ltMemberRepository.findPointByUid(uid).getPoint();
    }
    public List<LtProductCartDTO> showOrder(String[] chk){
        List<LtProductCartDTO> dtoList = new ArrayList<>();
        for(String cartNo : chk) {
            dtoList.add(ltProductCartRepository.findById(Integer.parseInt(cartNo)).get().toDTO());
        }
        return dtoList;
    }


    public CompletePage insertOrder(LtProductOrderDTO dto, String uid, String[] cartNo){
        dto.setOrdComplete(1); // 진행중
        dto.setOrdUid(uid);

        // 반환할 dto entity를 담을 list
        List<LtProductOrderItemDTO> dtoList = new ArrayList<>();

        //Order 입력
        int ordNo = ltProductOrderRepository.save(modelMapper.map(dto, LtProductOrderEntity.class)).getOrdNo();
        int i=0;
        for(String no : cartNo) {

            //OrderItem 입력
            LtProductCartEntity cartEntity = ltProductCartRepository.findById(Integer.parseInt(no)).get();
            LtProductEntity prodEntity = ltProductRepository.findById(cartEntity.getProdNo()).get();
            // 입력과 동시에 entityList에 입력
            LtProductOrderItemDTO tempDTO = ltProductOrderItemRepository.save(
                    prodEntity.toOrderItemEntity(
                        ordNo,
                        cartEntity.getProdNo(),
                        cartEntity.getCount(),
                        prodEntity.getPrice()*cartEntity.getCount()
                    )
            ).toDTO();
            //dto 추가필드
            tempDTO.setThumb1(prodEntity.getThumb1());
            tempDTO.setProdName(prodEntity.getProdName());
            tempDTO.setDescript(prodEntity.getDescript());
            dtoList.add(tempDTO);

            //Cart 삭제
            ltProductCartRepository.deleteById(Integer.parseInt(no));
            i++;
        }

        //사용 포인트 차감
        if(dto.getUsedPoint() > 0) {
            ltMemberRepository.updateMemberPointMinus(uid, dto.getUsedPoint());
            LtMemberPointEntity usedPointEntity = LtMemberPointEntity.builder()
                    .uid(uid)
                    .ordNo(ordNo)
                    .point(dto.getUsedPoint() * -1)
                    .build();
            ltMemberPointRepository.save(usedPointEntity);
        }

        //적립 포인트 추가
        if(dto.getSavePoint()>0) {
            ltMemberRepository.updateMemberPointPlus(uid, dto.getSavePoint());
            LtMemberPointEntity savedPointEntity = LtMemberPointEntity.builder()
                    .uid(uid)
                    .ordNo(ordNo)
                    .point(dto.getSavePoint())
                    .build();
            ltMemberPointRepository.save(savedPointEntity);
        }
        return CompletePage.builder()
                .dtoList(dtoList)
                .ordNo(ordNo)
                .build();
    }
}
