package dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class : batch
 * @Describe :
 * @Author : xSi
 * @Date : 2021/4/9 18:44
 * @Version : 1.0
 */
public class BatchInsert {

    @Test
    public void ids() throws IOException {
        List<Integer> ids= new ArrayList<>();
        ids.add(1111);
        ids.add(2222);
        ids.add(3333);
        ids.add(4444);
        System.out.println(ids);
        ObjectMapper mapper=new ObjectMapper();
        String strIds = mapper.writeValueAsString(ids);
        System.out.println(strIds);

        List<Integer> iids =mapper.readValue(strIds,List.class);
        System.out.println(iids);
        ////////////////////////
        System.out.println("============");
        Map<String,Integer> map=new HashMap<>();
        map.put("1",2);
        map.put("2",11);
        map.put("3",111);
        map.put("4",11111);
        map.put("5",111);
        map.put("6",111111111);
        System.out.println(map);
    }

}
