package com.uni.de.qvto.aop.monitor;

import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtOperationalEvaluationVisitorImpl;
import org.eclipse.m2m.internal.qvt.oml.expressions.MappingOperation;

import com.uni.de.qvto.tranformation.handlers.UML2RDBHandler;


public aspect TranformMonitor {
	
	pointcut qvtoTrace() : call(protected * org.eclipse.m2m.internal.qvt.oml.evaluator.QvtOperationalEvaluationVisitorImpl.executeMapping(..));
	
	before(QvtOperationalEvaluationVisitorImpl mon, MappingOperation mappingOperation) : qvtoTrace() && args(mappingOperation) && target(mon) {
		
		System.out.println("qvto mapping name---"+mappingOperation.getName());
	}
	
	pointcut callsToDone() : call(public void com.uni.de.qvto.tranformation.handlers.UML2RDBHandler.printUML2RDB(..));

	before(UML2RDBHandler mon, String obj) : callsToDone() && args(obj) && target(mon) {
	
		System.out.println(obj);
	}


}