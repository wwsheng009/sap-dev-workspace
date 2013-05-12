//王卫生 2013-2-6
//用户输入RFC函数名，并点击搜索函数
//使用函数Ext.getCmp()查找页面上的元素。
//使用函数getRawValue()获取文本框里的文本
//treePanel.getSelectionModel().on('select', function(selModel, record) 为树节点组件加上事件。

Ext.require(['*']);

    Ext.onReady(function(){
    	var layoutExamples = [];

    	//树节点的数据定义
    	var treeStore = Ext.create('Ext.data.TreeStore', {
    	    root: {
    	        expanded: true,
    	        children: [
    	            { id:"detention",text: "detention", leaf: true },
    	            { id:"homework",text: "homework", expanded: true, children: [
    	                { id:"report",text: "book report", leaf: true },
    	                { id:"alegrbra",text: "alegrbra", leaf: true}
    	            ] },
    	            { id:"lottery",text: "buy lottery tickets", leaf: true }
    	        ]
    	    }
    	});

    	var treePanel = Ext.create('Ext.tree.Panel', {
    	   // title: 'Simple Tree',
    	  //  width: 200,
    	  //  height: 150,
    	    store: treeStore,
    	    rootVisible: false,
    	   // renderTo: Ext.getBody()
    	});
    	//事件处理，为树控件附加上Select事件。
        // Assign the changeLayout function to be called on tree node click.
        treePanel.getSelectionModel().on('select', function(selModel, record) {
            if (record.get('leaf')) {
            	alert(record.getId() + '-panel');
                //Ext.getCmp('content-panel').layout.setActiveItem(record.getId() + '-panel');
            }
        });
        
        var inputForm = Ext.create('Ext.form.Panel',{
        	layout:{
        		type:'hbox',
        		
        	},
        	items:[{
        			id:'rfcFuncionName',
        	       	xtype:'textfield',
        	       
        	       	fieldLabel:'RFC function Name',
        	       allowBlank:false
        	       },{
        	    	   xtype:'button',
        	    	   text:'Search',
        	    	   handler:function(){
        	    		   alert(Ext.getCmp('rfcFuncionName').getRawValue());

        	    		   store.load({url:'rfc/getfunctionlist.action?functionName=' + Ext.getCmp('rfcFuncionName').getRawValue()});
        	    	   }
        	       }]
        });
        
    	Ext.define('RFCFUNCModel',{
    		extend:'Ext.data.Model',
    		fields:['FUNCNAME','GROUPNAME','APPL','HOST','STEXT']
    	});
    	var store = Ext.create('Ext.data.JsonStore',{
    		model:'RFCFUNCModel',
    		proxy:{
    			type:'ajax',
    			url:'rfc/getfunctionlist.action',
    			//url:'rfc/getfunctionlist.action',
    			reader:{
    				type:'json',
    				//root:'images'
    			}
    		}
    	});
    	//store.load();
    	
    	var listView = Ext.create('Ext.grid.Panel',{
    		//width:800,
    		height:650,
    		//autoScroll : true,
    		//collapsible:true,
    		title:'Simple ListView<i>(0 items selected)</i>',
    		//renderTo:Ext.getBody(),
    		store:store,
    		multiSelect:true,
    		viewConfig:{
    			emptyText:'No items to display'
    		},
    		columns:[{
    			text:'Function',
    			flex:50,
    			dataIndex:'FUNCNAME'
    		},{
    			text:'Group Name',
    			dataIndex:'GROUPNAME'
    			
    		},{
    			text:'APPL',
    			dataIndex:'APPL',
    			
    		},{
    			text:'HOST',
    			dataIndex:'HOST'
    		},{
    			text:'STEXT',
    			dataIndex:'STEXT'
    		}]
    	});
    	
        var item6 = listView;

        var item1 = Ext.create('Ext.Panel', {
            title: 'Accordion Item 1',
            html: '&lt;empty panel&gt;',
            cls:'empty',
            items:[treePanel]
        });
        
            var item2 = Ext.create('Ext.Panel', {
                title: 'Accordion Item 2',
                html: '&lt;empty panel&gt;',
                cls:'empty'
            });

            var item3 = Ext.create('Ext.Panel', {
                title: 'Accordion Item 3',
                html: '&lt;empty panel&gt;',
                cls:'empty'
            });

            var item4 = Ext.create('Ext.Panel', {
                title: 'Accordion Item 4',
                html: '&lt;empty panel&gt;',
                cls:'empty'
            });

            var item5 = Ext.create('Ext.Panel', {
                title: 'Accordion Item 5',
                html: '&lt;empty panel&gt;',
                cls:'empty'
            });

            var accordion = Ext.create('Ext.Panel', {
                title: 'Accordion',
                collapsible: true,
                region:'west',
                margins:'5 0 5 5',
                split:true,
                width: 210,
                layout:'accordion',
                items: [
                        item1, 
                        item2, item3, item4, item5]
            });
            


            
            layoutExamples.push(inputForm);
            layoutExamples.push(item6);
            
            
        	var contentPanel = {
       	         id: 'content-panel',
       	         region: 'center', // this is what makes this panel into a region within the containing layout
       	      //   layout: 'card',
       	         margins: '5 5 5 0',
       	         cls:'empty',
       	         activeItem: 0,
       	    //     border: false,
       	         items: layoutExamples
       	    };
            var viewport = Ext.create('Ext.Viewport', {
                layout:'border',
                items:[
                    accordion,
                    contentPanel,
                    ]
            });
        });