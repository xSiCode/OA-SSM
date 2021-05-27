package com.th.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.th.dao.NoticeReceiverMapper;
import com.th.entity.Meeting;
import com.th.entity.Notice;
import com.th.entity.NoticeReceiver;
import com.th.service.NoticeService;
import com.th.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨天发
 * @since 2021-05-22
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private Notice notice;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeReceiver noticeReceiver;
    @Autowired
    private NoticeReceiverMapper noticeReceiverMapper;


    /* - - - - - - - - - - - - - -   以下为新建/修改 (发送，存草稿)  公告   - - - - - - - - - - - - -  */
    @PostMapping("insert")
    public ResponseData insert(@RequestBody Map<String, Object> map){
        //将数据放入service层处理，并返回成功插入后的会议id
        Integer getNoticeId = null;
        try {
            getNoticeId = noticeService.insertNotice(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
        if (getNoticeId > 0) {
            //插入成功，返回插入后的会议id
            return ResponseData.SUCCESS().extendData("getNoticeId", getNoticeId);
        } else {
            return ResponseData.FAIL().extendData("msg", "插入失败");
        }
    }
    /* - - - - - - - - - - - - - -   以下为查看发起的公告详情   - - - - - - - - - - - - -  */
    @PostMapping("getCreatNoticeDetailById")
    public ResponseData getCreatNoticeDetailById(@RequestBody Map<String, Integer> map) {
        Integer currentId = map.get("currentNoticeId");
        Notice currentNotice = noticeService.getCreatNoticeDetailById(currentId);
        if (currentNotice != null) {
            return ResponseData.SUCCESS().extendData("currentNotice", currentNotice);
        } else {
            return ResponseData.FAIL().extendData("msg", "查无该公告");
        }
    }
    /* - - - - - - - - - - - - - -   以下为删除  隐藏（未发送）的 公告   - - - - - - - - - - - - -  */
    @PostMapping("creatorDeleteNoticeById")
    public ResponseData creatorDeleteNoticeById(@RequestBody List< Map<String,Object> > listMap ) {
        try {
            boolean delete =   noticeService.CreatorDeleteNotice(listMap);
            return ResponseData.SUCCESS().extendData("flag",delete);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseData.FAIL();
        }

    }
    /* - - - - - - - - - - - - - -   以下为查询发起的公告（展示/隐藏）历史缩略图   - - - - - - - - - - - - -  */
    @PostMapping("listNoticeByCreator")
    public ResponseData listNoticeByCreator(@RequestBody Map<String, Integer> map) {
        Integer needPage = map.get("needPage");       //分页参数
        Integer currentCreatorId =map.get("creatorId");
        PageHelper.startPage(needPage, 7);
        try {
            List<Notice> listMap = noticeService.listNoticeByCreator(currentCreatorId);
            if (listMap != null) {
                PageInfo page = new PageInfo(listMap, 7);
                return ResponseData.SUCCESS().extendData("listNotice", page);
            }else {
                return ResponseData.FAIL().extendData("msg","没有该发起者对应状态的信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }
    /* - - - - - - - - - - - - - -   以下为查看接收到的公告缩略图   - - - - - - - - - - - - -  */
    @PostMapping("listNoticeByReceiver")
    public ResponseData listNoticeByReceiver(@RequestBody Map<String, Integer> map) {
        Integer needPage = map.get("needPage");       //分页参数
        Integer receiverId =map.get("receiverId");
        PageHelper.startPage(needPage, 8);
        try {
            List< Map<String,Object> > listMap = noticeService.listNoticeByReceiver(receiverId);
            if (listMap != null) {
                PageInfo page = new PageInfo(listMap, 8);
                return ResponseData.SUCCESS().extendData("listNotice", page);
            }else {
                return ResponseData.FAIL().extendData("msg","没有该接收者对应状态的信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    /* - - - - - - - - - - - - - -   以下为查看接收到的公告详情   - - - - - - - - - - - - -  */
    @PostMapping("getReceiverNoticeDetailById")
    public ResponseData getReceiverNoticeDetailById(@RequestBody Map<String, Integer> map) {
        try {
            Notice currentNotice = noticeService.getReceiverNoticeDetailById(map);
            if (currentNotice != null) {
                return ResponseData.SUCCESS().extendData("currentNotice", currentNotice);
            } else {
                return ResponseData.FAIL().extendData("msg", "查无该公告");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.ERROR();
        }
    }

    /* - - - - - - - - - - - - - -   以下为删除接收到的公告- - - - - - - - - - - - -  */
    @PostMapping("receiverDeleteNoticeById")
    public ResponseData receiverDeleteNoticeById(@RequestBody List< Map<String,Object> > listMap) {
        try {
            boolean delete =   noticeService.receiverDeleteNotice(listMap);
            return ResponseData.SUCCESS().extendData("flag",delete);
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseData.FAIL();
        }

    }
}

