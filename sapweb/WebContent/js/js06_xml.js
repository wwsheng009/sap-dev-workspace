//王卫生 2013-2-6
//用户输入RFC函数名，并点击搜索函数
//使用函数Ext.getCmp()查找页面上的元素。
//使用函数getRawValue()获取文本框里的文本
//treePanel.getSelectionModel().on('select', function(selModel, record) 为树节点组件加上事件。

Ext.require([ '*' ]);

Ext.onReady(function() {
	var layoutExamples = [];

	// 树节点的数据定义
	var treeStore = Ext.create('Ext.data.TreeStore', {
		root : {
			expanded : true,
			children : [ {
				id : "detention",
				text : "detention",
				leaf : true
			}, {
				id : "homework",
				text : "homework",
				expanded : true,
				children : [ {
					id : "report",
					text : "book report",
					leaf : true
				}, {
					id : "alegrbra",
					text : "alegrbra",
					leaf : true
				} ]
			}, {
				id : "lottery",
				text : "buy lottery tickets",
				leaf : true
			} ]
		}
	});

	var treePanel = Ext.create('Ext.tree.Panel', {
		// title: 'Simple Tree',
		// width: 200,
		// height: 150,
		store : treeStore,
		rootVisible : false,
	// renderTo: Ext.getBody()
	});
	// 事件处理，为树控件附加上Select事件。
	// Assign the changeLayout function to be called on tree node click.
	treePanel.getSelectionModel().on('select', function(selModel, record) {
		if (record.get('leaf')) {
			alert(record.getId() + '-panel');
			// Ext.getCmp('content-panel').layout.setActiveItem(record.getId() +
			// '-panel');
		}
	});

	// 动态生成的grid panel
	var storeFields = [];
	var gridColumns = [];
	var dystore;
	var item7; // 可以动态加入到ViewPort里，动态创建的gridPanel

	var clickEvent = function() {
		// alert(Ext.getCmp('rfcFuncionName').getRawValue());
		store.load({
			url : 'rfc/readtable.action?tableName='
					+ Ext.getCmp('rfcFuncionName').getRawValue()
//			url : 'http://localhost:8080/sapweb/temp/test.xml'
		});
	}

	var loadDataEvent = function() {
		// 需要根据现有的store创建动态的model field 和 column
		var columns = [];
		store.each(function(r) {
			columns.push(r.getData());
		});
		for ( var i = 0; i < columns.length; i++) {
			var columnName = columns[i].name;
			var storeField = columns[i].name;

			storeFields[i] = {};
			storeFields[i].name = storeField;
			storeFields[i].type = 'text';

			gridColumns[i] = {};
			gridColumns[i].text = columnName;
			gridColumns[i].width = 100;
			gridColumns[i].dataIndex = storeField;
		}

		dystore = Ext.create('Ext.data.Store', {
			fields : storeFields,
			data : [],
			// model:'RFCFUNCModel',
			proxy : {
				type : 'ajax',
				url : 'rfc/readtable.action?tableName=T132',
				// url:'http://localhost:8080/sapweb/temp/test.xml',
				// url:'rfc/getfunctionlist.action',
				reader : {
					type : 'xml',
					record : 'row',
					root : 'rows'
				// root:'images'
				}
			}
		});

		item7 = Ext.create('Ext.grid.Panel', {
			title : 'Simple ListView<i>(0 items selected)</i>',
			store : dystore,
			columns : gridColumns,
			height : 300
		});
		String
		reqStr = "rfc/readtable.action?tableName=";
		reqStr += Ext.getCmp('rfcFuncionName').getRawValue();
		reqStr += "&rowCount=";
		reqStr += Ext.getCmp('rowCount').getRawValue();
		dystore.load({
			url : reqStr
//			url : "http://localhost:8080/sapweb/temp/test.xml"
		});
		// Ext.getCmp('content-panel').items.add(item7);
		item8.reconfigure(dystore, gridColumns);
	}
	var inputForm = Ext.create('Ext.form.Panel', {
		layout : {
			type : 'hbox',
		},
		items : [ {
			id : 'rfcFuncionName',
			xtype : 'textfield',
			fieldLabel : 'RFC function Name',
			allowBlank : false
		}, {
			xtype : 'button',
			text : 'Search',
			handler : clickEvent
		}, {
			id : 'rowCount',
			xtype : 'textfield',
			fieldLablel : 'Rows:',
			allowBlank : false,
			value : 100
		}, {
			xtype : 'button',
			text : 'loadData',
			handler : loadDataEvent,
		} ]
	});

	Ext.define('RFCFUNCModel', {
		extend : 'Ext.data.Model',
		fields : [ 'name', 'type', 'typename', 'caption' ]
	});
	var store = Ext.create('Ext.data.Store', {
		model : 'RFCFUNCModel',
		proxy : {
			type : 'ajax',
			url : 'rfc/readtable.action?tableName=T132',
			// url:'http://localhost:8080/sapweb/temp/test.xml',
			reader : {
				type : 'xml',
				record : 'column',
				root : 'columns'
			}
		}
	});
	// store.load();

	var listView = Ext.create('Ext.grid.Panel', {
		// width:800,
		height : 300,
		// autoScroll : true,
		collapsible:true,
		title : 'Simple ListView<i>(0 items selected)</i>',
		// renderTo:Ext.getBody(),
		store : store,
		multiSelect : true,
		viewConfig : {
			emptyText : 'No items to display'
		},
		columns : [ {
			text : 'name',
			flex : 50,
			dataIndex : 'name'
		}, {
			text : 'type',
			dataIndex : 'type'

		}, {
			text : 'typename',
			dataIndex : 'typename'

		}, {
			text : 'caption',
			dataIndex : 'caption'
		} ]
	});

	var item6 = listView;
	//用于显示数据
	var item8 = Ext.create('Ext.grid.Panel', {
		// width:800,
		height : 300,
		// autoScroll : true,
		// collapsible:true,
		title : 'Simple ListView<i>(0 items selected)</i>',
		// renderTo:Ext.getBody(),
		store : dystore,
		multiSelect : true,
		viewConfig : {
			emptyText : 'No items to display'
		},
		columns : []
	});

	var item1 = Ext.create('Ext.Panel', {
		title : 'Accordion Item 1',
		html : '&lt;empty panel&gt;',
		cls : 'empty',
		items : [ treePanel ]
	});

	var item2 = Ext.create('Ext.Panel', {
		title : 'Accordion Item 2',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	});

	var item3 = Ext.create('Ext.Panel', {
		title : 'Accordion Item 3',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	});

	var item4 = Ext.create('Ext.Panel', {
		title : 'Accordion Item 4',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	});

	var item5 = Ext.create('Ext.Panel', {
		title : 'Accordion Item 5',
		html : '&lt;empty panel&gt;',
		cls : 'empty'
	});

	var accordion = Ext.create('Ext.Panel', {
		title : 'Accordion',
		collapsible : true,
		region : 'west',
		margins : '5 0 5 5',
		split : true,
		width : 210,
		layout : 'accordion',
		items : [ item1, item2, item3, item4, item5 ]
	});

	layoutExamples.push(inputForm);
	layoutExamples.push(item6);
	layoutExamples.push(item8);

	var contentPanel = {
		id : 'content-panel',
		region : 'center', // this is what makes this panel into a region
							// within the containing layout
		// layout: 'card',
		margins : '5 5 5 0',
		cls : 'empty',
		activeItem : 0,
		// border: false,
		items : layoutExamples
	};
	var viewport = Ext.create('Ext.Viewport', {
		layout : 'border',
		items : [ accordion, contentPanel, ]
	});
});