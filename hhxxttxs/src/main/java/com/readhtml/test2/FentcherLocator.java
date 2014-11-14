package com.readhtml.test2;

import java.util.HashMap;
import java.util.Map;


public class FentcherLocator {
	
	private static FentcherLocator fentcherLocator=new FentcherLocator();
	private static Map<String,CatalogFentcher> fentcherMap=new HashMap<String,CatalogFentcher>();
	
	private FentcherLocator(){};
	
	public static FentcherLocator getInstance(){
		return fentcherLocator;
	}
	
	public CatalogFentcher getFentcher(String filename){
		String FenthcerName=getFenthcerNameByFileName(filename);
		return fentcherMap.get(FenthcerName);
	}
	
	public void register(String key,CatalogFentcher cf){
		fentcherMap.put(key, cf);
	}
	
	public void unregister(String key){
		fentcherMap.remove(key);
	}

	private String[] fileStartingNames={"财务顾问咨询每日快讯","行业研究每日要参","流动性与市场风险每周每日预警","信贷风险高层参考","银行经营与管理日报",
			"每日财经要参","银行家每日要参","中小金融营销法眼与经营解码一周播报","财务顾问咨询每周热点","产业政策周刊","对公理财市场观察","风险经理实务研究每周要览",
			"电子银行周刊","公司业务创新和营销每周要览","公司业务客户经理周刊","金融监管政策周刊","理财观察","零售银行同业监测周报","流动性与市场风险周报",
			"投行资产管理市场周刊","投资银行业务动态","行业数据周刊","行业重点事件每周深度解析","银行卡","中国债务市场观察","三农产业金融动态监测报告","信贷产品与授信动态",
			"行业热点专题分析报告","中国信贷风险报告","中国信贷风险专题分析报告","中国银行业公司业务创新与营销专题研究报告","小微金融产品及服务模式创新",
			"信用担保业务与金融创新跟踪研究","中国中小企业金融服务跟踪分析报告","中小金融市场营销与授信专题","中小企业产品创新案例分析","个人理财业务月度专题报告",
			"《风险预警月度动态监测报告》","《全国城市商业银行业务动态月度监测报告》","《战略性新兴产业月度跟踪分析报告》","安徽省同业对公业务竞争监测报告",
			"北京、上海、江苏等六省市同业对公业务竞争监测报告","财务顾问信息月刊","大连市房地产行业月度热点精析","电子银行创新月度报告","电子银行动态监测",
			"广东省银行经营环境月度监测报告【区域经济发展】","广西区银行经营环境月度监测报告【区域经济发展】","贵州省同业对公业务竞争监测报告","商业银行金融创新动态月度监测",
			"省份月度房地产数据分析","城市常州月度房地产数据分析","信用卡产品创新与营销月度研究","行业供应链融资方案","中国&四川省宏观经济与政策研究报告",
			"中国房地产行业月度热点精析","资产配置建议月报","小微金融竞争力分析月度报告","商业银行小微业务创新专题","大数据技术应用于零售银行转型与创新研究",
			"电子银行业务聚焦","个人消费贷款产品解析","商业银行网点转型与创新研究","银行房地产行业研究专题报告"};
	
	private String getFenthcerNameByFileName(String fileName){
		for(String name:fileStartingNames){
			if(fileName.contains(name)){
				return name;
			}
		}
		return "通用解析器";
	}
	
}
