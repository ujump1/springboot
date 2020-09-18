package com.yj.springboot;

import com.yj.springboot.service.rulesEngine.RuleEngineConditionService;
import com.yj.springboot.service.utils.ConditionUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class RuleEngineConditionServiceTest extends BaseTest {

	@Autowired
	private RuleEngineConditionService ruleEngineConditionService;

	@Test
	public void properties(){
		String conditionObjectCode = "com.yj.springboot.entity.rulesEngine.condition.ElectronicInvoiceCondition";
		Map<String,String> map = new HashMap<>();
		Map<String, Object> mapInit = new HashMap<>();
		Map<String, Object> mapValue = new HashMap<>();
		Map<String,String> mapName = new HashMap<>();
		Object initObject = null;
		Object o = null;
		String id = "878FB496-8692-11E9-B74E-0266B6F39528";
		try {
			 map=ruleEngineConditionService.properties(conditionObjectCode,true);
			 mapInit = ruleEngineConditionService.initPropertiesAndValues(conditionObjectCode);
			initObject = ruleEngineConditionService.getInit(conditionObjectCode);
			 mapValue = ruleEngineConditionService.propertiesAndValues(conditionObjectCode,id,true);
			 o = ruleEngineConditionService.get(conditionObjectCode,id,true);
			mapName = ruleEngineConditionService.getAllCondition();
		} catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		String shellContext = "a.amount > 100;";
		Map<String,Object> mapCondition = new HashMap<>();
		mapCondition.put("a",o);
		boolean result = ConditionUtil.groovyTest(shellContext,mapCondition);
		System.out.println(map);

	}

}
