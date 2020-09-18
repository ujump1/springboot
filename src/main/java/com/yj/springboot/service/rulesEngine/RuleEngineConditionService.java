package com.yj.springboot.service.rulesEngine;


import com.yj.springboot.api.IRuleEngineConditionService;
import com.yj.springboot.entity.rulesEngine.condition.ElectronicInvoiceCondition;
import com.yj.springboot.service.utils.ExpressionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RuleEngineConditionService implements IRuleEngineConditionService {

	@Autowired
	private RuleEngineCommonConditionService ruleEngineCommonConditionService;

	@Override
	public Map<String, String> properties(String conditionObjectCode, Boolean all) throws ClassNotFoundException {
		return ruleEngineCommonConditionService.properties(conditionObjectCode,all);
	}

	@Override
	public Map<String, Object> initPropertiesAndValues(String conditionObjectCode) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
		return ruleEngineCommonConditionService.initPropertiesAndValues(conditionObjectCode);
	}

	@Override
	public Object getInit(String conditionObjectCode) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
		return ruleEngineCommonConditionService.getInit(conditionObjectCode);
	}

	@Override
	public Map<String, Object> propertiesAndValues(String conditionObjectCode, String id, Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		if(StringUtils.isBlank(conditionObjectCode)){
			return new HashMap<>();
		}
		Map<String, Object> map = new HashMap<>();
		switch (conditionObjectCode) {
			case "com.ecmp.brm.act.entity.rulesEngine.condition.ElectronicInvoiceCondition":
				return propertiesAndValuesForElectronicInvoiceCondition(id,all);
			case "test" :
				break;
			default:
				;
		}
		return ruleEngineCommonConditionService.propertiesAndValues(conditionObjectCode,id,all);
	}

	@Override
	public Object get(String conditionObjectCode, String id, Boolean all) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		return ruleEngineCommonConditionService.getObject(conditionObjectCode,this.propertiesAndValues(conditionObjectCode,id,all));
	}

	@Override
	public Map<String, String> getAllCondition() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		return ruleEngineCommonConditionService.getAllCondition();
	}


	public Map<String, Object> propertiesAndValuesForElectronicInvoiceCondition(String id, Boolean all) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
		ElectronicInvoiceCondition electronicInvoiceCondition = new  ElectronicInvoiceCondition();
		return new ExpressionUtil<ElectronicInvoiceCondition>().getPropertiesAndValues(electronicInvoiceCondition, all);
	}
}
