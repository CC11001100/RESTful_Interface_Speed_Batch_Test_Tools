## RESTful



1. 会自动填需要的参数，原理是通过解析服务器的相应来实现的，所以要使用之前请先将服务器的异常处理页面啥的都先关掉，不然无法自动解析的。
		默认的已经提供了springmvc @RequestParam(required=true)在jetty 400页面的解析实现，
		更多扩展：
			1. 实现org.cc11001100.web.test.process.ParamLackProcessor接口，
			2. 然后UrlScheduler#setParamLackProcessor(ParamLackProcessor paramLackProcessor)















