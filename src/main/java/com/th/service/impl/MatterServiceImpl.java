package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import com.th.entity.Matter;
import com.th.dao.MatterMapper;
import com.th.entity.MatterAttachment;
import com.th.entity.MatterContentConfig;
import com.th.entity.MatterHandler;
import com.th.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.th.utils.DataTransfer;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-12
 */
@Service
public class MatterServiceImpl extends ServiceImpl<MatterMapper, Matter> implements MatterService {

    @Autowired
    Matter matter;

    @Autowired
    MatterMapper matterMapper;

    @Autowired
    UserService userService;

    @Autowired
    MatterHandlerService matterHandlerService;

    @Autowired
    MatterAttachmentService matterAttachmentService;

    @Autowired
    MatterContentConfigService matterContentConfigService;

    @Override
    public Matter getMatterByMatterId(Integer matterId) {

        Matter currentMatter = matterMapper.selectMatterEntityByMatterId(matterId);
        return currentMatter;
    }

    @Override
    public List<Map<String, Object>> getMatterHandlerBriefByUser(Integer currentId, String matterStatus) {
        //加个详细的处理状态
        List<Map<String, Object>> listMap = baseMapper.selectMatterHandlerBriefByUser(currentId, matterStatus);
        System.out.println(listMap);
        String timeLimitStr = "";
        String completedTimeStr = "";
        String currentTImeStr = "";
        int flag = 0;
        if ("待办".equals(matterStatus)) {    //处理者 没有完成时间,只能用截止时间跟当前时间比较
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentTImeStr = dateTimeFormatter.format(localDateTime);

            for (Map<String, Object> map : listMap) {    //遍历每一个map 并根据时间比较，改变状态
                timeLimitStr = map.get("timeLimit").toString();
                flag = currentTImeStr.compareTo(timeLimitStr); //<=0 ,代办  >0 未完成
                if (flag > 0) {
                    map.put("state", "未完成");
                }
            }
        } else if ("已办".equals(matterStatus)) {
            for (Map<String, Object> map : listMap) {
                timeLimitStr = (String) map.get("timeLimit").toString();
                completedTimeStr = (String) map.get("completedTime").toString();
                flag = completedTimeStr.compareTo(timeLimitStr);// <=0 已办  ； >0 截止日期小于完成日期：超时完成。
                if (flag > 0) {
                    map.put("state", "超时完成");
                }
            }
        }
        return listMap;
    }

    @Override
    public List<Map<String, Object>> getMatterCreatorBriefByUser(Integer currentId, String matterStatus) {
       // String status = "";
        List<Map<String, Object>> maps = new ArrayList<>();
        //待发，已发 区分。
        if ("待发".equals(matterStatus)) {
            maps = baseMapper.selectMatterCreatorDraftBriefByUser(currentId);
        } else if ("已发".equals(matterStatus)) {
            maps = baseMapper.selectMatterCreatorSubmitBriefByUser(currentId);
        }


        return maps;
    }

    @Override
    public Integer insertMatter(Map<String, Object> map) {
        //分步骤 遍历map 得到  基本matter，attachment,config,handler
        //1.1得到matter 基本信息
        String title = (String) map.get("title");
        String contentText = (String) map.get("contentText");
        Integer creatorId = (Integer) map.get("creatorId");
        LocalDateTime sendTime = DataTransfer.parseStringToDate((String) map.get("startTime"));
        LocalDateTime deadlineTime = DataTransfer.parseStringToDate((String) map.get("limitTime"));
        String status = (String) map.get("status");//已发
        //1.2 封装到 matter 里，只含有基本信息
        //以上的数据在t_matter中缺少 id 和 content_config_id;接下来开始封装
        Matter currentMatter = new Matter();
        currentMatter.setTitle(title);
        currentMatter.setContentText(contentText);
        currentMatter.setCreatorId(creatorId);
        currentMatter.setSendTime(sendTime);
        currentMatter.setDeadlineTime(deadlineTime);
        currentMatter.setStatus(status);
        int insert = baseMapper.insert(currentMatter);
        int matterId = -1;
        if (insert > 0) {
            matterId = currentMatter.getId();
        } else {
            System.out.println("matter:封装失败");
            return -1;
        }
        /*=================================================================*/
        //2.1得到处理人id ，再加上 matterId 最后封装到t_matter_handler
        List<Integer> handlerIds = (List<Integer>) map.get("handlers");
        MatterHandler currentHandler = new MatterHandler();
        boolean saveHandler = false;
        for (Integer currentHandlerId : handlerIds) {
            currentHandler.setMatterId(matterId);
            currentHandler.setHandlerId(currentHandlerId);
             saveHandler = matterHandlerService.save(currentHandler);
            //插入数据库中，数据库中，matter_status 默认为待办
            if (saveHandler == false) {
                System.out.println("mattherHandler:插入失败");
                return -1;
            }
        }
        /*=================================================================*/
        //3.1得到附件     获得前端传过来的附件数组，[{},{},{},]
        List<Map<String, String>> fileList = (List<Map<String, String>>) map.get("fileList");
        //3.2 构造于entity对应的集合   以下两步构造符合 entity的 attachment
        //3.3  将3.1 数据赋值给3.2.
        MatterAttachment currentAttachment = new MatterAttachment();
        boolean saveAttachment = false;
        for (Map<String, String> currentFile : fileList) {
            currentAttachment.setName(currentFile.get("name"));
            currentAttachment.setData(currentFile.get("url"));
            //关联 对应的matter_id
            currentAttachment.setMatterId(matterId);
             saveAttachment = matterAttachmentService.save(currentAttachment);
            if (saveAttachment == false) {
                System.out.println("needInsertAttachments  插入失败");
                return -1;
            }
        }


        /*=================================================================*/
//        //4 得到 文本的配置文件
        Map<String, String> contentConfig = (Map<String, String>) map.get("setConfig");
        MatterContentConfig currentConfig = new MatterContentConfig();
        currentConfig.setFontBgcolor(contentConfig.get("fontBgcolor"));
        currentConfig.setFontColor(contentConfig.get("fontColor"));
        currentConfig.setFontFamily(contentConfig.get("fontFamily"));
        currentConfig.setFontOblique(contentConfig.get("fontOblique"));
        currentConfig.setFontSize(contentConfig.get("fontSize"));
        currentConfig.setFontWeight(contentConfig.get("fontWeight"));
        currentConfig.setLineHeight(contentConfig.get("lineHeight"));
        currentConfig.setTextAlign(contentConfig.get("textAlign"));
        currentConfig.setTextDecoration(contentConfig.get("textDecoration"));

        boolean save = matterContentConfigService.save(currentConfig);
        if (save == true) {
            //将matterContentConfigService操作后得到的主键赋值给 t_matter
            currentMatter.setContentConfigId(currentConfig.getId());
            int i = baseMapper.updateById(currentMatter);
            if (i < 0) {
                System.out.println("currentConfig 插入matter失败");
                return -1;
            }
        }
        return matterId;
    }

    @Override
    public Integer insertDraft(Map<String, Object> map) {
//        此方法与insertMatter的唯一区别就是，可能存在null值，因此需要判断每一个属性的值
        //分步骤 遍历map 得到  基本matter，attachment,config,handler
        //1.1得到matter 基本信息
        // 以下3条前端必须传 title过来
        String title = (String) map.get("title");
        Integer creatorId = (Integer) map.get("creatorId");
        String status = (String) map.get("status");
        //判断是否为空，是否使用参数传过来的值
        String contentText = (String) map.get("contentText");
        String sendTimeStr = (String) map.get("startTime");
        String deadlineTimeStr = (String) map.get("limitTime");
        if(contentText == null || contentText.isEmpty()){
            contentText="你的事项暂无内容:by通航学院办公自动化";
        }
        LocalDateTime sendTime =  LocalDateTime.now();
        LocalDateTime deadlineTime = LocalDateTime.now();
        if(sendTimeStr != null && sendTimeStr.length()>0 ){
            sendTime = DataTransfer.parseStringToDate((String) map.get("startTime"));
        }
        if(deadlineTimeStr != null && deadlineTimeStr.length()>0 ){
            deadlineTime = DataTransfer.parseStringToDate((String) map.get("limitTime"));
        }

        //1.2 封装到 matter 里，只含有基本信息
        //以上的数据在t_matter中缺少 id 和 content_config_id;接下来开始封装
        Matter currentMatter = new Matter();
        currentMatter.setTitle(title);
        currentMatter.setContentText(contentText);
        currentMatter.setCreatorId(creatorId);
        currentMatter.setSendTime(sendTime);
        currentMatter.setDeadlineTime(deadlineTime);
        currentMatter.setStatus(status);//"草稿"
        int insert = baseMapper.insert(currentMatter);
        int matterId = -1;
        if (insert > 0) {
            matterId = currentMatter.getId();
        } else {
            System.out.println("matter:封装失败");
            return -1;
        }
        /*=================================================================*/
        //2.1得到处理人id ，再加上 matterId 最后封装到t_matter_handler
        List<Integer> handlerIds = (List<Integer>) map.get("handlers");
        if( ! handlerIds.isEmpty()){
            System.out.println("- - - - - - -1 - - - - - - -");
            System.out.println(handlerIds);

            MatterHandler currentHandler = new MatterHandler();
            boolean saveHandler = false;
            for (Integer currentHandlerId : handlerIds) {
                currentHandler.setMatterId(matterId);
                currentHandler.setHandlerId(currentHandlerId);
                saveHandler = matterHandlerService.save(currentHandler);

                System.out.println("- - - - - -2 - - - - - - - -");
                System.out.println(currentHandler);

                //插入数据库中，数据库中，matter_status 默认为待办
                if (saveHandler == false) {
                    System.out.println("mattherHandler:插入失败");
                    return -1;
                }
            }
        }

        /*=================================================================*/
        //3.1得到附件     获得前端传过来的附件数组，[{},{},{},]
        List<Map<String, String>> fileList = (List<Map<String, String>>) map.get("fileList");
        //3.2 构造于entity对应的集合   以下两步构造符合 entity的 attachment
        //3.3  将3.1 数据赋值给3.2.

        if(!fileList.isEmpty()){
            System.out.println("- - - - - - a- - - - - - - -");
            System.out.println(fileList);

            MatterAttachment currentAttachment = new MatterAttachment();
            boolean saveAttachment = false;
            for (Map<String, String> currentFile : fileList) {
                currentAttachment.setName(currentFile.get("name"));
                currentAttachment.setData(currentFile.get("url"));
                //关联 对应的matter_id
                currentAttachment.setMatterId(matterId);


                System.out.println("- - - - - -b - - - - - - - -");
                saveAttachment = matterAttachmentService.save(currentAttachment);
                if (saveAttachment == false) {
                    System.out.println("needInsertAttachments  插入失败");
                    return -1;
                }

            }
        }




        /*=================================================================*/
//        //4 得到 文本的配置文件
        Map<String, String> contentConfig = (Map<String, String>) map.get("setConfig");
        if( !contentConfig.isEmpty() ){
            MatterContentConfig currentConfig = new MatterContentConfig();
            currentConfig.setFontBgcolor(contentConfig.get("fontBgcolor"));
            currentConfig.setFontColor(contentConfig.get("fontColor"));
            currentConfig.setFontFamily(contentConfig.get("fontFamily"));
            currentConfig.setFontOblique(contentConfig.get("fontOblique"));
            currentConfig.setFontSize(contentConfig.get("fontSize"));
            currentConfig.setFontWeight(contentConfig.get("fontWeight"));
            currentConfig.setLineHeight(contentConfig.get("lineHeight"));
            currentConfig.setTextAlign(contentConfig.get("textAlign"));
            currentConfig.setTextDecoration(contentConfig.get("textDecoration"));

            boolean save = matterContentConfigService.save(currentConfig);
            if (save == true) {
                //将matterContentConfigService操作后得到的主键赋值给 t_matter
                currentMatter.setContentConfigId(currentConfig.getId());
                int i = baseMapper.updateById(currentMatter);
                if (i > 0) {
                } else {
                    System.out.println("currentConfig 插入matter失败");
                    return -1;
                }
            }
        }
        return matterId;
    }

    @Override
    public Integer deleteMatterBatch(List<Map<String,Object>> listMap) {
        // 四种状态 只有 已办/待发  可以删除 事项
        for( Map<String,Object> currentMap : listMap){
            Integer integer = deleteMatter(currentMap);
            if(integer<0){
                System.out.println("删除事项失败");
                return -1;
            }
        }
        return 1;
    }

    @Override
    public List<Map<String,Object>> getMatterByMatterName(Map<String, Object> map) {
        String matterStatus=  (String) map.get("matterStatus");
        String needMatterName=  (String) map.get("matterTitle");
        Integer currentUserId = (Integer) map.get("userId");
        if ("待发".equals(matterStatus) ||"已发".equals(matterStatus)){
            //从事项发送者的角度
            List<Map<String, Object>> maps = new ArrayList<>();
            //待发，已发 区分。
            if ("待发".equals(matterStatus)) {
                maps = baseMapper.selectMatterCreatorDraftBriefByUserLike(currentUserId,needMatterName);
            } else if ("已发".equals(matterStatus)) {
                maps = baseMapper.selectMatterCreatorSubmitBriefByUserLike(currentUserId,needMatterName);
            }
            return maps;
        }
        /*==============================================================================================*/
        if("待办".equals(matterStatus) || "已办".equals(matterStatus) ){
            //加个详细的处理状态
            List<Map<String, Object>> listMap = baseMapper.selectMatterHandlerBriefByUserLike(currentUserId, matterStatus,needMatterName);
            System.out.println(listMap);
            String timeLimitStr = "";
            String completedTimeStr = "";
            String currentTImeStr = "";
            int flag = 0;
            if ("待办".equals(matterStatus)) {    //处理者 没有完成时间,只能用截止时间跟当前时间比较
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                currentTImeStr = dateTimeFormatter.format(localDateTime);

                for (Map<String, Object> currentMap : listMap) {    //遍历每一个map 并根据时间比较，改变状态
                    timeLimitStr = currentMap.get("timeLimit").toString();
                    flag = currentTImeStr.compareTo(timeLimitStr); //<=0 ,代办  >0 未完成
                    if (flag > 0) {
                        currentMap.put("state", "未完成");
                    }
                }
            } else if ("已办".equals(matterStatus)) {
                for (Map<String, Object> currentMap : listMap) {
                    timeLimitStr = (String) currentMap.get("timeLimit").toString();
                    completedTimeStr = (String) currentMap.get("completedTime").toString();
                    flag = completedTimeStr.compareTo(timeLimitStr);// <=0 已办  ； >0 截止日期小于完成日期：超时完成。
                    if (flag > 0) {
                        currentMap.put("state", "超时完成");
                    }
                }
            }
            return listMap;
        }
        System.out.println("没找到");
        return null;
    }

    //用于批量删除。
    private Integer deleteMatter(Map<String, Object> map) {
        // 四种状态 只有 已办/待发  可以删除 事项
        String matterStatus = (String) map.get("matterStatus");
        Integer currentMatterId = (Integer) map.get("currentMatterId");
        Integer currentUserId   = (Integer) map.get("currentUserId");
        if("已办".equals(matterStatus)){
            //删除自己这个处理人，其他不用实现文本格式配置，附件不用删除
            boolean removeHandler = matterHandlerService.remove(new QueryWrapper<MatterHandler>()
                    .eq("matter_id",currentMatterId)
                    .eq("matter_status","已办")
                    .eq("handler_id",currentUserId));
            if( removeHandler !=true)  {
                System.out.println("移除removeHandler 失败");
                return -1;
            }
            //
        }else if("待发".equals(matterStatus)){
            Matter currentMatter = baseMapper.selectOne(new QueryWrapper<Matter>()
                    .eq("id", currentMatterId)
                    .eq("creator_id", currentUserId)
                    .eq("status","草稿"));
            //删除关联的附件
            System.out.println(currentMatterId);

            //判断该事项是否有对应的附件
            MatterAttachment currentAttachment = matterAttachmentService.getOne(new QueryWrapper<MatterAttachment>()
                    .eq("matter_id", currentMatterId));
            if( currentAttachment !=null  ){
                boolean b = matterAttachmentService.remove(new QueryWrapper<MatterAttachment>()
                        .eq("matter_id",currentMatterId));
                if(b!= true){
                    System.out.println("删除附件失败");
                    return -1;
                }
            }
            //判断该事项是否有对应的处理人
            MatterHandler currentMatterHandler = matterHandlerService.getOne(new QueryWrapper<MatterHandler>().eq("matter_id", currentMatterId));
            if(currentMatterHandler!=null){
                boolean matter_id = matterHandlerService.remove(new QueryWrapper<MatterHandler>().eq("matter_id", currentMatterId));
                if(matter_id!= true){
                    System.out.println("删除处理人失败");
                    return -1;
                }
            }

            //删除事项
            //因为configId是外键值，所以，我这里选择先提取去configId,再删除事项。  或者先不matter.configId置空，再删除config
            Integer currentConfigId =currentMatter.getContentConfigId();
            System.out.println(currentConfigId);
            int i = baseMapper.deleteById(currentMatterId);
            if(i<0){
                System.out.println("删除事项出错");
                return -1;
            }
            //删除 关联的 文本格式文件
             if( currentConfigId !=null ){
                 boolean removeById = matterContentConfigService.removeById(currentConfigId);
                 if(removeById!= true){
                     System.out.println("删除文本格式文件失败");
                     return -1;
                 }
             }
        }else {
            System.out.println("参数错误");
            return -1;
        }

        return 1;
    }

}
