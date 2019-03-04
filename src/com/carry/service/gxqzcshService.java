package com.carry.service;

import com.carry.dao.BaseDao;
import com.carry.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class gxqzcshService {
    public String  queryObjectOut(String jkbh,String queryXmlDoc){
        String msg = "";
        String returnMsg = "";
        Map gxqzcshMap = CreateXml.analysisShXml(queryXmlDoc);
        String jgdm = (String) gxqzcshMap.get("jgdm");
        String qymc = (String) gxqzcshMap.get("qymc");
        BaseDao baseDao = (BaseDao) SpringContextUtils.getBean("baseDao");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String jywDate = sdf.format(new Date());
        String jkbhStr = jgdm+"XdzGa0x!ntongjitjbbcyzcsh"+jywDate;
        String flag = "0";
        try {
            String jkbhjyw = new MD5Util().getMD5ofStr(jgdm+"XdzGa0x!ntongjitjbbcyzcsh"+jywDate);
            int year = Integer.parseInt(DateUtil.getDateFormatStr(new Date(), "yyyy"));
            year--;
            Map<String,Object> params = new HashMap<String,Object>();
            String[] monthArr = {"02","03","04","05","06","07","08","09","10","11","12"};
            String sbzlrq = DateUtil.getDateFormatStr(DateUtil.getNewDateByAddDay(new Date(),3),"MM月dd日");
            String lxrinfo = "联系地址：都市之门A座1810室 \n" +
                    "联系电话：17392576647    88824206\n" +
                    "联系人：宋美君\n";
            if(StringUtil.equals(jkbhjyw,jkbh)){
                StringBuffer sql = new StringBuffer("select a.*,nvl(b.ybcnt,0)ybcnt, nvl(b.sbyf,-1)sbyf,c.nbshzt from (select a.corp_id, a.base01,a.shgzxym,a.base02, a.qylx,a.zt from corp_jbxx a where 1=1 ");
                if(jgdm.length()==18){
                    sql.append(" and upper(shgzxym)=upper(:jgdm) or upper(base01)=upper(substr(:jgdm,9,9))) a");
                }else if(jgdm.length()==9){
                    sql.append(" and upper(base01)=upper(:jgdm) or upper(substr(shgzxym,9,9))=upper(:jgdm)) a");
                }else{
                    flag = "-1";
                    msg = "不正确的组织机构代码或统一社会信用代码";
                }
                if(StringUtil.isEmpty(msg)){
                    sql.append(" left join (select tat01.corp_id,to_char(wm_concat(substr(report_date, 5, 2)))sbyf, count(*) as ybcnt from tat01");
                    sql.append("  where substr(report_date, 0, 4) = :year group by corp_id) b");
                    sql.append(" on a.corp_id = b.corp_id");
                    sql.append(" inner join (select corp_id,(case when shzt='1' and tjzt='1' then 1 else 0 end)nbshzt ");
                    sql.append("  from hjnb left join hjnb_sh on hjnb.hjnbid=hjnb_sh.hjnbid where report_year=:year)c on a.corp_id=c.corp_id");
                    params.put("jgdm",jgdm);
                    params.put("year",year);
                    Map<String,Object> dataMap = baseDao.getMapBySQL(sql.toString(),params);
                    if(!StringUtil.ObjectIsEmpty(dataMap)){
                        String[] sjsbArr = dataMap.get("SBYF").toString().split(",");
                        String wsbyf = this.removeContainString(monthArr,sjsbArr);
                        Date afterDate = DateUtil.getDateByFormat("2019-04-20","yyyy-MM-dd");
                        String ysrq = DateUtil.getDateFormatStr(new Date(),"yyyy年MM月dd日");
                        String nf = StringUtil.equals("7",dataMap.get("ZT").toString())?"2017":StringUtil.equals("9",dataMap.get("ZT").toString())?"2018":"";
                        if(StringUtil.equals("1",dataMap.get("QYLX").toString()) || StringUtil.equals("3",dataMap.get("QYLX").toString()) || StringUtil.equals("5",dataMap.get("QYLX").toString()) || StringUtil.equals("7",dataMap.get("QYLX").toString()) || StringUtil.equals("8",dataMap.get("QYLX").toString()) || StringUtil.equals("9",dataMap.get("QYLX").toString())){//四上企业
                            //四上企业审批
                            if(StringUtil.equals("1",dataMap.get("NBSHZT").toString())){
                                flag="1";
                                msg = "一审通过。";
                            }else{
                                if((StringUtil.equals("7",dataMap.get("ZT").toString())||StringUtil.equals("9",dataMap.get("ZT").toString()))){
                                    //四上企业 2017,2018整改
                                    if(DateUtil.isAfterDate(afterDate)){//4-22之后
                                        msg = "贵公司已于"+nf+"年作出按时上报统计报表的承诺，但仍未上报2018年火炬年报，不符合按时上报统计报表的要求，予以驳回。";
                                    }else{//4-22之前
                                        msg = "贵公司未上报2018年火炬年报，请公司务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内登录https://tjbb.xdz.gov.cn/gxqy/填写年报，并将审核通过后的年报打印盖章报送至统计局。"+lxrinfo;
                                    }
                                }else if (DateUtil.isAfterDate(afterDate)){//4-22之后
                                    msg = "贵公司未上报2018年火炬年报，请公司法人务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内携带以下资料至统计局：1、统计年报纸质表；2、承诺书（法人签字，公司加盖公章）；3、营业执照副本原件、法人身份证。"+lxrinfo;
                                } else {//4-22之前
                                    msg = "贵公司未上报2018年火炬年报，请公司务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内登录https://tjbb.xdz.gov.cn/gxqy/填写年报，并将审核通过后的年报打印盖章报送至统计局。"+lxrinfo;
                                }
                            }
                        }else{//非四上企业
                           if(MathUtil.toInt(dataMap.get("YBCNT").toString(),0)>=8){
                               //非四上企业月报上报大于等于8个月
                               if(StringUtil.equals("1",dataMap.get("NBSHZT").toString())){
                                   flag="1";
                                   msg = "一审通过。";
                               }else{
                                   if((StringUtil.equals("7",dataMap.get("ZT").toString())||StringUtil.equals("9",dataMap.get("ZT").toString()))){
                                       //非四上企业 2017,2018整改
                                       if(DateUtil.isAfterDate(afterDate)){//4-22之后
                                           msg = "贵公司已于"+nf+"年作出按时上报统计报表的承诺，但仍未上报2018年火炬年报，不符合按时上报统计报表的要求，予以驳回。";
                                       }else{//4-22之前
                                           msg = "贵公司未上报2018年火炬年报，请公司务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内登录https://tjbb.xdz.gov.cn/gxqy/填写年报，并将审核通过后的年报打印盖章报送至统计局。";
                                       }
                                   }else if (DateUtil.isAfterDate(afterDate)){//4-22之后
                                       msg = "贵公司未上报2018年火炬年报，请公司法人务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内携带以下资料至统计局：1、统计年报纸质表；2、承诺书（法人签字，公司加盖公章）；3、营业执照副本原件、法人身份证。"+lxrinfo;
                                   } else {//4-22之前
                                       msg = "贵公司未上报2018年火炬年报，请公司务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内登录https://tjbb.xdz.gov.cn/gxqy/填写年报，并将审核通过后的年报打印盖章报送至统计局。"+lxrinfo;
                                   }
                               }
                           }else{
                               if(StringUtil.equals("1",dataMap.get("NBSHZT").toString())){
                                   if((StringUtil.equals("7",dataMap.get("ZT").toString())||StringUtil.equals("9",dataMap.get("ZT").toString()))){
                                       msg = "贵公司已于"+nf+"年作出按时上报统计报表的承诺，但仍有2018年"+wsbyf+"月未按时上报统计月报，不符合按时上报统计报表的要求，予以驳回。";
                                   }else{
                                       msg = "贵公司未上报2018年"+wsbyf+"月<高新区统计月报表>，请公司法人务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内携带以下资料至统计局：1、缺失月份的纸质报表，每月一张加盖公章；2、承诺书（法人签字，公司加盖公章）；3、营业执照副本原件、法人身份证。";
                                   }
                               }else{
                                   if((StringUtil.equals("7",dataMap.get("ZT").toString())||StringUtil.equals("9",dataMap.get("ZT").toString()))){
                                       if(DateUtil.isAfterDate(afterDate)){//4-22之后
                                           msg = "贵公司已于"+nf+"年作出按时上报统计报表的承诺，但仍有2018年"+wsbyf+"月未按时上报统计月报及2018年火炬年报,不符合按时上报统计报表的要求，予以驳回。";
                                       }else{//4-22之前
                                           msg = "贵公司已于"+nf+"年作出按时上报统计报表的承诺，但仍有2018年"+wsbyf+"月未按时上报统计月报及2018年火炬年报,不符合按时上报统计报表的要求，予以驳回。";
                                       }
                                   }else if (DateUtil.isAfterDate(afterDate)){//4-22之后
                                       msg = "贵公司未上报2018年"+wsbyf+"月<高新区统计月报表>及火炬年报，请公司务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内登录https://tjbb.xdz.gov.cn/gxqy/填写年报，公司法人务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内携带以下资料至统计局：1、缺失月份的纸质报表，每月一张加盖公章，火炬年报；2、承诺书（法人签字，公司加盖公章）；3、营业执照副本原件、法人身份证。"+lxrinfo;
                                   } else {//4-22之前
                                       msg = "4月20日后驳回意见为：贵公司未上报2018年"+wsbyf+"月<高新区统计月报表>及火炬年报，请公司法人务必于一审审批日"+ysrq+"（不含审批日）起五个工作日内携带以下资料至统计局：1、缺失月份的纸质报表，每月一张加盖公章，火炬年报；2、承诺书（法人签字，公司加盖公章）；3、营业执照副本原件、法人身份证。"+lxrinfo;
                                   }
                               }
                           }
                        }
                    }else{
                        msg = "贵公司未与统计局建立统计关系，请公司务必于"+sbzlrq+"前携带以下资料至统计局：1、营业执照副本原件。";
                    }
                }
            }else{
                flag = "-1";
                msg = "非法调用接口";
            }
            returnMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><body><code>"+flag+"</code><message>"+msg+"</message></body></root>";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "调用的接口出现异常，请联系后台技术人员";
            returnMsg = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><body><code>-1</code><message>"+msg+"</message></body></root>";
        }
            String fwrzSql = "insert into FWRZ(fwsj,jkbm,fwxx,fhxx,fwbj) " +
                    "values(sysdate,'"+jkbhStr+"','"+queryXmlDoc+"','"+returnMsg+"','"+flag+"')";
            baseDao.executeSql(fwrzSql,null);
        return returnMsg;
    }

    public String removeContainString(String[] monthArr,String[] sjsbArr){
        List<String> monthList = new ArrayList();
        for(String st:monthArr){
            if(!monthList.contains(st)){
                monthList.add(st);
            }
        }
        for(String st1:sjsbArr){
            if(monthList.contains(st1)){
                monthList.remove(st1);
            }
        }
        String[] strArray = monthList.toArray(new String[monthList.size()]);
        if(strArray.length==11){
            return "2月-12";
        }
        return Arrays.toString(strArray);
    }

}
