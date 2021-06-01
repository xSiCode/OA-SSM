package service;

import com.th.dao.HomePageMapper;
import com.th.dao.SearchMapper;
import com.th.dao.TimeViewMapper;
import com.th.service.HomePageService;
import com.th.service.TimeViewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Class : TimeViewTest
 * @Describe :
 * @Author : xSi
 * @Date : 2021/5/27 13:14
 * @Version : 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class TimeViewTest {
    @Autowired
    private TimeViewService timeViewService;
    @Autowired
    private TimeViewMapper timeViewMapper;

    @Test
    public void selectMatter(){
        LocalDateTime start = LocalDateTime.of(2020,1,1,0,0,0);
        LocalDateTime end = LocalDateTime.of(2022,1,1,0,0,0);
        Integer userId = 1004;
        System.out.println(" - - - - -- - - - - - - matter - - - - - -- - - - - - - -");
        List<Map<String ,String>> matter=  timeViewMapper.selectMatter(userId , start , end);
        System.out.println(matter);
        System.out.println(" - - - - -- - - - - - - meeting - - - - - -- - - - - - - -");
        List<Map<String ,String>> meeting=  timeViewMapper.selectMeeting(userId , start , end);
        System.out.println(meeting);
        System.out.println(" - - - - -- - - - - - - plan - - - - - -- - - - - - - -");
        List<Map<String ,String>> plan=  timeViewMapper.selectPlan(userId , start , end);
        System.out.println(plan);
        System.out.println(" - - - - -- - - - - - - audit - - - - - -- - - - - - - -");
        List<Map<String ,String>> audit=  timeViewMapper.selectAudit(userId , start , end);
        System.out.println(audit);

    }


    @Test
    public void datetimeT(){
        LocalDate start = LocalDate.of(2021,5,25);
        LocalDate end = LocalDate.of(2022,1,1);
        LocalDateTime localDateTime = start.atTime(8, 1, 1);
        LocalDate endPlus = end.plusDays(2);

        System.out.println(  start.getDayOfWeek()  );
        System.out.println( localDateTime );
        System.out.println(endPlus);
    }
    //
    @Test
    public void jwt(){

    }
    @Autowired
    private SearchMapper searchMapper;

    @Test
    public void getResultByTitle(){
        //测试 搜索
        List<Map<String, Object>> result = searchMapper.getResultByTitle(1004, "毕业生");


        System.out.println(result);
    }

    @Autowired
    private HomePageService homePageService;

    @Test
    public void listView(){
        homePageService.listView(1004);
    }

    @Autowired
    private HomePageMapper homePageMapper;

    @Test
    public void selectViewPlan(){
        List<Map<String, Object>> maps = homePageMapper.selectViewPlan(1171);
        System.out.println(maps);
    }

    @Test
    public void listTimeViewDot(){
        Integer userId = 1171 ;
        LocalDateTime start = LocalDate.of(2021,5,1).atTime(0, 0, 0);
        LocalDateTime end = LocalDate.of(2021,5,31).atTime(0, 0, 0);

        Integer startDay = start.getDayOfMonth();
        Integer startMonth = start.getMonthValue();

        LocalDateTime localDate = start.plusDays(11);
        System.out.println(localDate);

        System.out.println(startDay+"fff " +startMonth);

        Object todoNum = homePageMapper.countPlan(userId, start, end);
        System.out.println(todoNum);
    }


    @Test
    public void countTodo(){
        Integer userId = 1171 ;
        LocalDateTime start = LocalDate.of(2021,5,3).atTime(7, 0, 0);
        LocalDateTime end = LocalDate.of(2021,5,3).atTime(23, 0, 0);

        Integer integer = homePageMapper.countMeeting(userId, start, end);
        System.out.println(integer);
    }

}