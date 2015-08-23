package cn.freeliver.config
{
	import flash.system.System;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	public class ConfigService
	{
		private var globalConfig:Array;
		private var application:Object;
		private var remoteObject:RemoteObject=new RemoteObject("cn.freeliver.config.ConfigService");
		
		public function ConfigService(application:Object)
		{
			this.application=application as Application;
			remoteObject.getGlobalConfig.addEventListener(ResultEvent.RESULT,this.getGlobalConfigResultListener);
			remoteObject.getGlobalConfig.addEventListener(FaultEvent.FAULT,this.getGlobalConfigFaultListener);
			remoteObject.getGlobalConfig();
		}
		
		private function getGlobalConfigResultListener(event:ResultEvent):void{
			globalConfig=event.result as Array;
			var quickMenu:Array=globalConfig[0].split(",");
			application.quickMenu.dataProvider=quickMenu;
			application.userInformation.htmlText="欢迎您："+globalConfig[1]+" 角色：<font color=\"#FF0000\">"+globalConfig[2]+"</font> 当前时间："+globalConfig[3];
			application.homePageModule.welcome_text.htmlText="欢迎："+globalConfig[1]+" 您所属的角色：<font color=\"#FF0000\">"+globalConfig[2]+"</font> <br/>上次登陆时间："+globalConfig[4];
			application.homePageModule.welcome_text.htmlText+="<br/>上次登陆的IP地址："+globalConfig[5];
			
			application.homePageModule.runtime_text.htmlText="当前VM版本："+System.vmVersion;
			application.homePageModule.runtime_text.htmlText+=" 使用内存："+System.totalMemory/1024+" kb";
			application.homePageModule.runtime_text.htmlText+="<br/>";		
			application.homePageModule.runtime_text.htmlText+="当前浏览器版本："+globalConfig[6];
			application.homePageModule.runtime_text.htmlText+="<br/>";					
			application.homePageModule.runtime_text.htmlText+="所使用的系统："+globalConfig[7];
		}
		private function getGlobalConfigFaultListener(event:FaultEvent):void{
			Alert.show(event.message.toString());
		}
		public function configGlobalConfig():void{
			remoteObject.getGlobalConfig();
		}


		

	}
}