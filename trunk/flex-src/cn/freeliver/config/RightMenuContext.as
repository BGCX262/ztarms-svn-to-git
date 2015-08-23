package cn.freeliver.config
{
	import flash.ui.*;
	public class RightMenuContext
	{
		private var contextMenu:ContextMenu;
		private var application:Object;
		public function RightMenuContext(application:Object)
		{
			this.application=application;
			contextMenu=new ContextMenu();	
			this.removeBuildInMenu();

			this.buildRMSContextMenuItem();
			this.application.contextMenu=contextMenu;
		}
		private function removeBuildInMenu():void{
			contextMenu.hideBuiltInItems();

		}
		private function buildRMSContextMenuItem():void{
			var cmItemTag:ContextMenuItem=new ContextMenuItem('科研管理系统v0.1');
			var cmItemLookVer:ContextMenuItem=new ContextMenuItem('版权申明',true);			
			var cmItemLookAuthor:ContextMenuItem=new ContextMenuItem('联系作者');
			contextMenu.customItems.push(cmItemTag);
			contextMenu.customItems.push(cmItemLookAuthor);			
			contextMenu.customItems.push(cmItemLookVer);
		}

	}
}