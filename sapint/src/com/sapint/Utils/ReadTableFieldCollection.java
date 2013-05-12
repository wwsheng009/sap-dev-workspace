package com.sapint.Utils;

import java.util.ArrayList;

 @SuppressWarnings("serial")
class ReadTableFieldCollection extends ArrayList<ReadTableField> {



	@Override
	public ReadTableField get(int arg0) {
		return (ReadTableField) super.get(arg0);
	}
	
	protected int GetOverallLength()
    {
		int num = 0;
		for (ReadTableField field: this){
				num += field.getLength();
			
		}
		return num;
    }
}
