package com.th.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import com.th.entity.*;
import com.th.dao.MatterMapper;
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
    MatterService matterService;

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
        String timeLimitStr = "";
        String completedTimeStr = "";
        String currentTimeStr = "";
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentTimeStr = dateTimeFormatter.format(localDateTime);
        int flag = 0;
        if ("待办".equals(matterStatus)) {    //处理者 没有完成时间,只能用截止时间跟当前时间比较
            for (Map<String, Object> map : listMap) {    //遍历每一个map 并根据时间比较，改变状态
                timeLimitStr = map.get("timeLimit").toString();
                flag = currentTimeStr.compareTo(timeLimitStr); //<=0 ,代办  >0 未完成
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
        //若获取到了id 则该数据之前是草稿，则需要的处理为 覆盖
        Integer ifId = (Integer) map.get("id"); //只有他 可能没有数据
        //一次性得到所有数据
        String title = (String) map.get("title");
        String contentText = (String) map.get("contentText");
        Integer creatorId = (Integer) map.get("creatorId");
        LocalDateTime sendTime = DataTransfer.parseStringToDate((String) map.get("startTime"));
        LocalDateTime deadlineTime = DataTransfer.parseStringToDate((String) map.get("limitTime"));
        String status = (String) map.get("status");//已发
        //新建对象
        Matter currentMatter = new Matter();
        MatterContentConfig currentConfig = new MatterContentConfig();
        MatterHandler currentHandler = new MatterHandler();
        MatterAttachment currentAttachment = new MatterAttachment();
        //根据 是否为首次插入分流
        if (ifId == null) {
            //首次新增，直接插入
            /*     === = = = = = = = ==   config  = = = = = = = = = == = = = = = = = */
            Map<String, String> contentConfig = (Map<String, String>) map.get("setConfig");
            currentConfig.setFontBgcolor(contentConfig.get("fontBgcolor"));
            currentConfig.setFontColor(contentConfig.get("fontColor"));
            currentConfig.setFontFamily(contentConfig.get("fontFamily"));
            currentConfig.setFontOblique(contentConfig.get("fontOblique"));
            currentConfig.setFontSize(contentConfig.get("fontSize"));
            currentConfig.setFontWeight(contentConfig.get("fontWeight"));
            currentConfig.setLineHeight(contentConfig.get("lineHeight"));
            currentConfig.setTextAlign(contentConfig.get("textAlign"));
            currentConfig.setTextDecoration(contentConfig.get("textDecoration"));
            boolean saveConfig = matterContentConfigService.save(currentConfig);
            if (!saveConfig) {
                System.out.println("currentConfig 插入失败");
                return -1;
            }
            /*     === = = = = = = = ==   matter  = = = = = = = = = == = = = = = = = */
            currentMatter.setContentConfigId(currentConfig.getId());
            currentMatter.setTitle(title);
            currentMatter.setContentText(contentText);
            currentMatter.setCreatorId(creatorId);
            currentMatter.setSendTime(sendTime);
            currentMatter.setDeadlineTime(deadlineTime);
            currentMatter.setStatus("已发");//已发
            boolean saveMatter = matterService.save(currentMatter);
            if (!saveMatter) {
                System.out.println("保存matter失败");
                return -1;
            }
            /*     === = = = = = = = ==   handlers  = = = = = = = = = == = = = = = = = */
            List<Integer> handlerIds = (List<Integer>) map.get("handlers");
            for (Integer currentHandlerId : handlerIds) {
                currentHandler.setMatterId(currentMatter.getId());
                currentHandler.setHandlerId(currentHandlerId);
                currentHandler.setMatterStatus("待办");
                boolean saveHandler = matterHandlerService.save(currentHandler);
                //插入数据库中，数据库中，matter_status 默认为待办
                if (!saveHandler) {
                    System.out.println("Handler:插入失败");
                    return -1;
                }
            }
            /*     === = = = = = = = ==   attachment  = = = = = = = = = == = = = = = = = */
            List<Map<String, String>> fileList = (List<Map<String, String>>) map.get("fileList");
            //3.2 构造于entity对应的集合   以下两步构造符合 entity的 attachment
            //3.3  将3.1 数据赋值给3.2.
            for (Map<String, String> currentFile : fileList) {
                currentAttachment.setMatterId(currentMatter.getId());
                currentAttachment.setName(currentFile.get("name"));
                currentAttachment.setData(currentFile.get("url"));
                //关联 对应的matter_id
                boolean saveAttachment = matterAttachmentService.save(currentAttachment);
                if (!saveAttachment) {
                    System.out.println("Attachments  插入失败");
                    return -1;
                }
            }
        } else {
            //之前保存为草稿，下面将做数据替换
            //为了代码不更改，我这里通过 ifId 获取其他对应属性。
            Matter oldMatter = baseMapper.selectById(ifId);
            MatterContentConfig oldConfig = matterContentConfigService.getById(oldMatter.getContentConfigId());
            /*     === = = = = = = = ==   config  = = = = = = = = = == = = = = = = = */
            Map<String, String> contentConfig = (Map<String, String>) map.get("setConfig");
            currentConfig.setId(oldConfig.getId());
            currentConfig.setFontBgcolor(contentConfig.get("fontBgcolor"));
            currentConfig.setFontColor(contentConfig.get("fontColor"));
            currentConfig.setFontFamily(contentConfig.get("fontFamily"));
            currentConfig.setFontOblique(contentConfig.get("fontOblique"));
            currentConfig.setFontSize(contentConfig.get("fontSize"));
            currentConfig.setFontWeight(contentConfig.get("fontWeight"));
            currentConfig.setLineHeight(contentConfig.get("lineHeight"));
            currentConfig.setTextAlign(contentConfig.get("textAlign"));
            currentConfig.setTextDecoration(contentConfig.get("textDecoration"));
            boolean saveConfig = matterContentConfigService.updateById(currentConfig);
            if (saveConfig == false) {
                System.out.println("config  修改失败");
                return -1;
            }
            /*     === = = = = = = = ==   matter  = = = = = = = = = == = = = = = = = */
            currentMatter.setId(ifId);
            currentMatter.setContentConfigId(currentConfig.getId());
            currentMatter.setTitle(title);
            currentMatter.setContentText(contentText);
            currentMatter.setCreatorId(creatorId);
            currentMatter.setSendTime(sendTime);
            currentMatter.setDeadlineTime(deadlineTime);
            currentMatter.setStatus("已发");//已发
            boolean updateMatter = matterService.updateById(currentMatter);  //因为 之前已经有了id ,所以这里update
            if (updateMatter == false) {
                System.out.println("保存matter失败");
                return -1;
            }
            /*     === = = = = = = = ==   handlers  = = = = = = = = = == = = = = = = = */
            //由于handlers可以有多个，为了避免存草稿时，处理人员多，发送时，处理人员少造成的问题，这里先删除原来保存为草稿的处理人员信息
            //判断原来事项是否有处理人，有则删除
            List<MatterHandler> ifOldHandler = matterHandlerService
                    .list(new QueryWrapper<MatterHandler>().eq("matter_id", ifId));
            if (ifOldHandler != null || ifOldHandler.size() > 0) {
                //由于handlers可以有多个，为了避免存草稿时，处理人员多，发送时，处理人员少造成的问题，这里先删除原来保存为草稿的处理人员信息
                boolean deleteHandler = matterHandlerService
                        .remove(new QueryWrapper<MatterHandler>().eq("matter_id", ifId));
//                if (deleteHandler == false) {
//                    System.out.println("清楚处理人员失败");
//                    return -1;
//                }
            }
            List<Integer> handlerIds = (List<Integer>) map.get("handlers");
            for (Integer currentHandlerId : handlerIds) {
                currentHandler.setMatterId(currentMatter.getId());
                currentHandler.setHandlerId(currentHandlerId);
                currentHandler.setMatterStatus("待办");
                boolean saveHandler = matterHandlerService.save(currentHandler);
                //插入数据库中，数据库中，matter_status 默认为待办
                if (saveHandler == false) {
                    System.out.println("Handler:插入失败");
                    return -1;

                }
            }
            /*     === = = = = = = = ==   attachment  = = = = = = = = = == = = = = = = = */
            //需要原来的 草稿的 事项 对应的 附件得有，不然就是 remove(null) 删除所有了
            List<MatterAttachment> ifOldAttachment = matterAttachmentService
                    .list(new QueryWrapper<MatterAttachment>().eq("matter_id", ifId));
            if (ifOldAttachment != null || ifOldAttachment.size() > 0) {
                boolean deleteAttachment = matterAttachmentService
                        .remove(new QueryWrapper<MatterAttachment>().eq("matter_id", ifId));
//                if (deleteAttachment == false) {
//                    System.out.println("清除附件失败v -256");
//                    return -1;
//                }
            }
            List<Map<String, String>> fileList = (List<Map<String, String>>) map.get("fileList");
            //3.2 构造于entity对应的集合   以下两步构造符合 entity的 attachment
            //3.3  将3.1 数据赋值给3.2.
            for (Map<String, String> currentFile : fileList) {
                currentAttachment.setMatterId(currentMatter.getId());
                currentAttachment.setName(currentFile.get("name"));
                currentAttachment.setData(currentFile.get("url"));
                //关联 对应的matter_id
                boolean saveAttachment = matterAttachmentService.save(currentAttachment);
                if (saveAttachment == false) {
                    System.out.println("Attachments  插入失败");
                    return -1;
                }
            }
        }
        return currentMatter.getId();
    }

    @Override
    public Integer insertDraft(Map<String, Object> map) {
        //若获取到了id 则该数据之前是草稿，则需要的处理为 覆盖
        Integer ifId = (Integer) map.get("id"); //只有他 可能没有数据
        //一次性得到所有数据
        String title = (String) map.get("title");                    // json参数中必须有
        String contentText = (String) map.get("contentText");
        Integer creatorId = (Integer) map.get("creatorId");
        LocalDateTime sendTime = DataTransfer.parseStringToDate((String) map.get("startTime"));   // json参数中必须有
        LocalDateTime deadlineTime = null;
        String deadlineTimeStr = (String) map.get("limitTime");
        if (deadlineTimeStr != null && deadlineTimeStr.length() > 0) {
            deadlineTime = DataTransfer.parseStringToDate((String) map.get("limitTime"));
        }
        String status = (String) map.get("status");//待发
        //新建对象
        Matter currentMatter = new Matter();
        MatterContentConfig currentConfig = new MatterContentConfig();
        MatterHandler currentHandler = new MatterHandler();
        MatterAttachment currentAttachment = new MatterAttachment();
        //根据 是否为首次插入分流
        if (ifId == null) {
            //首次新增，直接插入
            /*     === = = = = = = = ==   config  = = = = = = = = = == = = = = = = = */
            Map<String, String> contentConfig = (Map<String, String>) map.get("setConfig");
            if (contentConfig != null) {
                currentConfig.setFontBgcolor(contentConfig.get("fontBgcolor"));
                currentConfig.setFontColor(contentConfig.get("fontColor"));
                currentConfig.setFontFamily(contentConfig.get("fontFamily"));
                currentConfig.setFontOblique(contentConfig.get("fontOblique"));
                currentConfig.setFontSize(contentConfig.get("fontSize"));
                currentConfig.setFontWeight(contentConfig.get("fontWeight"));
                currentConfig.setLineHeight(contentConfig.get("lineHeight"));
                currentConfig.setTextAlign(contentConfig.get("textAlign"));
                currentConfig.setTextDecoration(contentConfig.get("textDecoration"));
                boolean saveConfig = matterContentConfigService.save(currentConfig);
                if (saveConfig == false) {
                    System.out.println("currentConfig 插入失败");
                    return -1;
                }
            }
            /*     === = = = = = = = ==   matter  = = = = = = = = = == = = = = = = = */
            currentMatter.setContentConfigId(currentConfig.getId());   // 如果没用config ,问题也不大
            currentMatter.setTitle(title);
            currentMatter.setContentText(contentText);
            currentMatter.setCreatorId(creatorId);
            currentMatter.setSendTime(sendTime);
            currentMatter.setDeadlineTime(deadlineTime);
            currentMatter.setStatus("待发");//已发
            boolean saveMatter = matterService.save(currentMatter);
            if (saveMatter == false) {
                System.out.println("保存matter失败");
                return -1;
            }
            /*     === = = = = = = = ==   handlers  = = = = = = = = = == = = = = = = = */
            List<Integer> handlerIds = (List<Integer>) map.get("handlers");
            if (handlerIds != null) {
                for (Integer currentHandlerId : handlerIds) {
                    currentHandler.setMatterId(currentMatter.getId());
                    currentHandler.setHandlerId(currentHandlerId);
                    currentHandler.setMatterStatus("草稿");
                    boolean saveHandler = matterHandlerService.save(currentHandler);
                    //插入数据库中，数据库中，matter_status 默认为待办
                    if (saveHandler == false) {
                        System.out.println("Handler:插入失败");
                        return -1;
                    }
                }
            }
            /*     === = = = = = = = ==   attachment  = = = = = = = = = == = = = = = = = */
            List<Map<String, String>> fileList = (List<Map<String, String>>) map.get("fileList");
            if (fileList != null) {
                for (Map<String, String> currentFile : fileList) {
                    currentAttachment.setMatterId(currentMatter.getId());
                    currentAttachment.setName(currentFile.get("name"));
                    currentAttachment.setData(currentFile.get("url"));
                    //关联 对应的matter_id
                    boolean saveAttachment = matterAttachmentService.save(currentAttachment);
                    if (saveAttachment == false) {
                        System.out.println("Attachments  插入失败");
                        return -1;
                    }
                }
            }
            //  以上 id = null  的操作已经完成
        } else {
            //之前保存为草稿，下面将做数据替换
            //为了代码不更改，我这里通过 ifId 获取其他对应属性。
            Matter oldMatter = baseMapper.selectById(ifId);
            MatterContentConfig oldConfig = matterContentConfigService.getById(oldMatter.getContentConfigId());
            /*     === = = = = = = = ==   config  = = = = = = = = = == = = = = = = = */
            Map<String, String> contentConfig = (Map<String, String>) map.get("setConfig");
            if (contentConfig != null) {
                currentConfig.setId(oldConfig.getId());
                currentConfig.setFontBgcolor(contentConfig.get("fontBgcolor"));
                currentConfig.setFontColor(contentConfig.get("fontColor"));
                currentConfig.setFontFamily(contentConfig.get("fontFamily"));
                currentConfig.setFontOblique(contentConfig.get("fontOblique"));
                currentConfig.setFontSize(contentConfig.get("fontSize"));
                currentConfig.setFontWeight(contentConfig.get("fontWeight"));
                currentConfig.setLineHeight(contentConfig.get("lineHeight"));
                currentConfig.setTextAlign(contentConfig.get("textAlign"));
                currentConfig.setTextDecoration(contentConfig.get("textDecoration"));
                boolean saveConfig = matterContentConfigService.updateById(currentConfig);
                if (saveConfig == false) {
                    System.out.println("config  修改失败");
                    return -1;
                }
            }
            /*     === = = = = = = = ==   matter  = = = = = = = = = == = = = = = = = */
            currentMatter.setId(ifId);
            currentMatter.setContentConfigId(currentConfig.getId());
            currentMatter.setTitle(title);
            currentMatter.setContentText(contentText);
            currentMatter.setCreatorId(creatorId);
            currentMatter.setSendTime(sendTime);
            currentMatter.setDeadlineTime(deadlineTime);
            currentMatter.setStatus("待发");//存为草稿，事项状态为 待发
            boolean updateMatter = matterService.updateById(currentMatter);  //因为 之前已经有了id ,所以这里update
            if (updateMatter == false) {
                System.out.println("保存matter失败");
                return -1;
            }
            /*     === = = = = = = = ==   handlers  = = = = = = = = = == = = = = = = = */
            //由于handlers可以有多个，为了避免存草稿时，处理人员多，发送时，处理人员少造成的问题，这里先删除原来保存为草稿的处理人员信息
            //判断原来事项是否有处理人，有则删除
            List<MatterHandler> ifOldHandler = matterHandlerService
                    .list(new QueryWrapper<MatterHandler>().eq("matter_id", ifId));
            if (ifOldHandler != null && ifOldHandler.size() > 0) {
                boolean deleteHandler = matterHandlerService
                        .remove(new QueryWrapper<MatterHandler>().eq("matter_id", ifId));
                if (deleteHandler == false) {
                    System.out.println("清楚处理人员失败" + ifOldHandler + ifOldHandler.size());
                    return -1;
                }
            }
            List<Integer> handlerIds = (List<Integer>) map.get("handlers");
            for (Integer currentHandlerId : handlerIds) {
                currentHandler.setMatterId(currentMatter.getId());
                currentHandler.setHandlerId(currentHandlerId);
                currentHandler.setMatterStatus("草稿");
                boolean saveHandler = matterHandlerService.save(currentHandler);
                //插入数据库中，数据库中，matter_status 默认为待办
                if (saveHandler == false) {
                    System.out.println("Handler:插入失败");
                    return -1;
                }
            }
            /*     === = = = = = = = ==   attachment  = = = = = = = = = == = = = = = = = */
            //需要原来的 草稿的 事项 对应的 附件得有，不然就是 remove(null) 删除所有了
            List<MatterAttachment> ifOldAttachment = matterAttachmentService
                    .list(new QueryWrapper<MatterAttachment>().eq("matter_id", ifId));
            if (ifOldAttachment != null) {
                //原来的事项有对应 的附件，这里把它清除掉，再重新添加
                boolean deleteAttachment = matterAttachmentService
                        .remove(new QueryWrapper<MatterAttachment>().eq("matter_id", ifId));
//                if (deleteAttachment == false) {
//                    System.out.println("清除附件失败 435");
//                    return -1;
//                }
            }
            List<Map<String, String>> fileList = (List<Map<String, String>>) map.get("fileList");
            for (Map<String, String> currentFile : fileList) {
                currentAttachment.setMatterId(currentMatter.getId());
                currentAttachment.setName(currentFile.get("name"));
                currentAttachment.setData(currentFile.get("url"));
                //关联 对应的matter_id
                boolean saveAttachment = matterAttachmentService.save(currentAttachment);
                if (saveAttachment == false) {
                    System.out.println("Attachments  插入失败");
                    return -1;
                }
            }
        }
        return currentMatter.getId();


    }

    @Override
    public Integer deleteMatterBatch(List<Map<String, Object>> listMap) {
        // 四种状态 只有 已办/待发  可以删除 事项
        for (Map<String, Object> currentMap : listMap) {
            Integer integer = deleteMatter(currentMap);
            if (integer < 0) {
                System.out.println("删除事项失败");
                return -1;
            }
        }
        return 1;
    }

    @Override
    public List<Map<String, Object>> getMatterByMatterName(Map<String, Object> map) {
        String matterStatus = (String) map.get("matterStatus");
        String needMatterName = (String) map.get("matterTitle");
        Integer currentUserId = (Integer) map.get("userId");
        if ("待发".equals(matterStatus) || "已发".equals(matterStatus)) {
            //从事项发送者的角度
            List<Map<String, Object>> maps = new ArrayList<>();
            //待发，已发 区分。
            if ("待发".equals(matterStatus)) {
                maps = baseMapper.selectMatterCreatorDraftBriefByUserLike(currentUserId, needMatterName);
            } else if ("已发".equals(matterStatus)) {
                maps = baseMapper.selectMatterCreatorSubmitBriefByUserLike(currentUserId, needMatterName);
            }
            return maps;
        }
        /*==============================================================================================*/
        if ("待办".equals(matterStatus) || "已办".equals(matterStatus)) {
            //加个详细的处理状态
            List<Map<String, Object>> listMap = baseMapper.selectMatterHandlerBriefByUserLike(currentUserId, matterStatus, needMatterName);
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
            } else {
                for (Map<String, Object> currentMap : listMap) {
                    timeLimitStr = currentMap.get("timeLimit").toString();
                    completedTimeStr = currentMap.get("completedTime").toString();
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

    @Override
    public Integer completedMatter(Map<String, Integer> map) {
        Integer currentUserId = map.get("userId");
        Integer currentMatterId = map.get("matterId");
        LocalDateTime currentTime = LocalDateTime.now();
        MatterHandler currentMatterHandler = matterHandlerService
                .getOne(new QueryWrapper<MatterHandler>()
                        .eq("matter_id", currentMatterId)
                        .eq("handler_id", currentUserId));
        currentMatterHandler.setCompletedTime(currentTime);
        currentMatterHandler.setMatterStatus("已办");
        boolean updateById = matterHandlerService.updateById(currentMatterHandler);
        if (!updateById) {
            System.out.println("修改失败");
            return -1;
        }
        return currentMatterHandler.getMatterId();
    }

    //用于批量删除。
    private Integer deleteMatter(Map<String, Object> map) {
        // 四种状态 只有 已办/待发  可以删除 事项
        String matterStatus = (String) map.get("matterStatus");
        Integer currentMatterId = (Integer) map.get("currentMatterId");
        Integer currentUserId = (Integer) map.get("currentUserId");
        if ("已办".equals(matterStatus)) {
            //删除自己这个处理人，其他不用实现文本格式配置，附件不用删除
            boolean removeHandler = matterHandlerService.remove(new QueryWrapper<MatterHandler>()
                    .eq("matter_id", currentMatterId)
                    .eq("matter_status", "已办")
                    .eq("handler_id", currentUserId));
            if (removeHandler != true) {
                System.out.println("移除removeHandler 失败");
                return -1;
            }
            //
        } else if ("待发".equals(matterStatus)) {
            Matter currentMatter = baseMapper.selectOne(new QueryWrapper<Matter>()
                    .eq("id", currentMatterId)
                    .eq("creator_id", currentUserId)
                    .eq("status", "待发"));
            //删除关联的附件
            System.out.println(currentMatterId);

            //判断该事项是否有对应的附件
            List<MatterAttachment> deleteAttachment = matterAttachmentService.list(new QueryWrapper<MatterAttachment>()
                    .eq("matter_id", currentMatterId));
            if (deleteAttachment != null) {
                boolean b = matterAttachmentService.remove(new QueryWrapper<MatterAttachment>()
                        .eq("matter_id", currentMatterId));
//                if (b == false) {
//                    System.out.println("删除附件失败");
//                    return -1;
//                }
            }
            //判断该事项是否有对应的处理人
            List<MatterHandler> deleteHandlers = matterHandlerService.list(new QueryWrapper<MatterHandler>().eq("matter_id", currentMatterId));
            if (deleteHandlers != null) {
                boolean removeHandler = matterHandlerService.remove(new QueryWrapper<MatterHandler>().eq("matter_id", currentMatterId));
//                if (removeHandler ==false) {
//                    System.out.println("删除处理人失败");
//                    return -1;
//                }
            }

            //删除事项
            //因为configId是外键值，所以，我这里选择先提取去configId,再删除事项。  或者先不matter.configId置空，再删除config
            Integer currentConfigId = currentMatter.getContentConfigId();
            int i = baseMapper.deleteById(currentMatterId);
            if (i < 0) {
                System.out.println("删除事项出错");
                return -1;
            }
            //删除 关联的 文本格式文件
            if (currentConfigId != null) {
                boolean removeById = matterContentConfigService.removeById(currentConfigId);
                if (removeById != true) {
                    System.out.println("删除文本格式文件失败");
                    return -1;
                }
            }
        } else {
            System.out.println("参数错误");
            return -1;
        }

        return 1;
    }

}
