package com.uni.de.qvto.aop.monitor;

import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEvaluationEnv;
import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtOperationalEvaluationVisitorImpl;
import org.eclipse.m2m.internal.qvt.oml.expressions.MappingOperation;

public aspect TranformMonitor {
	
	pointcut qvtoTrace() : call(protected * org.eclipse.m2m.internal.qvt.oml.evaluator.QvtOperationalEvaluationVisitorImpl.executeMapping(..));
	
	before(QvtOperationalEvaluationVisitorImpl mon, MappingOperation mappingOperation, QvtOperationalEvaluationEnv evalEnv) : qvtoTrace() && args(mappingOperation, evalEnv) && target(mon) {
		
		// directly showing the acquired data
		System.out.println("qvto mapping name---"+mappingOperation.getName());
	}
}