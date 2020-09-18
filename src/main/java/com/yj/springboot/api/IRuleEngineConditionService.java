package com.yj.springboot.api;


import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("RuleEngineCondition")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "IRuleEngineConditionService 规则条件服务")
public interface IRuleEngineConditionService extends IConditionObjectService {

}
