package co.kr.lotteon.service;

import co.kr.lotteon.dto.LtCsCate1DTO;
import co.kr.lotteon.dto.LtCsCate2DTO;
import co.kr.lotteon.entity.LtCsCate1Entity;
import co.kr.lotteon.entity.LtCsCate2Entity;
import co.kr.lotteon.repository.LtCsCate1Repository;
import co.kr.lotteon.repository.LtCsCate2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsCateService {

    private final LtCsCate1Repository ltCsCate1Repository;
    private final LtCsCate2Repository ltCsCate2Repository;

    public List<LtCsCate1DTO> selectCSCate1s(){
        return ltCsCate1Repository.findAll().stream().map(LtCsCate1Entity::toDTO).collect(Collectors.toList());
    }
    public List<LtCsCate2DTO> selectCSCate2s(int cate1){
        return ltCsCate2Repository.findAllByCate1(cate1).stream().map(LtCsCate2Entity::toDTO).collect(Collectors.toList());
    }


    public Map<String, Object> getCsCates(){

        Map<String, Object> map = new HashMap<>();


        List<LtCsCate1DTO> cate1List = selectCSCate1s();

        List<Map> depth1 = new ArrayList<>();
        Map<Integer, List> depth2 = new HashMap<>();


        for (LtCsCate1DTO cate1 : cate1List) {
            //1차 카테고리 List에 MapObject로 저장 
            Map<String, String> depth1_temp = new HashMap<>();
            depth1_temp.put("cate1", cate1.getCate1()+"");
            depth1_temp.put("c1Name", cate1.getC1Name());
            depth1.add(depth1_temp);

            //2차 카테고리 data
            List<LtCsCate2DTO> cate2item = selectCSCate2s(cate1.getCate1());

            //2차 카테고리 Map에 MapObject로 저장 
            List<Map> depth2_2 = new ArrayList<>();
            Map<String,String> depth2_temp = null;
            for(LtCsCate2DTO dto2 : cate2item) {
                depth2_temp = new HashMap<>();
                depth2_temp.put("cate1", dto2.getCate1()+"");
                depth2_temp.put("cate2", dto2.getCate2()+"");
                depth2_temp.put("c2Name", dto2.getC2Name());
                depth2_2.add(depth2_temp);
            }
            depth2.put(cate1.getCate1(), depth2_2);
        }

        map.put("depth1", depth1);
        map.put("depth2", depth2);

        return map;
    }

}
