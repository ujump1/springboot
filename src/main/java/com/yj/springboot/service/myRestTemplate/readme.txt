1.这个包下面主要是展示怎么使用自定义配置RestTemplate
2.也可以在用最简单的就行
  @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
3.自定义自动配置可以参考config.defaultAutoConfig.java,config.properties.HttpClientPoolProperties,apiTemplate.*