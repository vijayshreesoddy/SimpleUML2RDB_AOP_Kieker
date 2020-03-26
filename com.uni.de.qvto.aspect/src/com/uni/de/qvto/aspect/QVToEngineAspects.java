package com.uni.de.qvto.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.m2m.internal.qvt.oml.expressions.MappingOperation;
import org.eclipse.m2m.qvt.oml.examples.blackbox.UtilitiesLibrary;


@Aspect
public class QVToEngineAspects {
	@Around("call(* org.eclipse.m2m.internal.qvt.oml.expressions.util.QVTOperationalVisitor.visitMappingOperation(..)) " +
		      "&& args(mappingOperation)")
		  public Object aroundCheckInConfiguration(final MappingOperation mappingOperation,final ProceedingJoinPoint joinPoint) throws Throwable
		  {
		    long startTime = UtilitiesLibrary.currentTime();

		    try
		    {
		      return (Object) joinPoint.proceed();
		    }
		    catch (Exception e)
		    {
		      throw e;
		    }
		    finally
		    {
		      long endTime = UtilitiesLibrary.currentTime();
		      
		      // using Kieker to log the acquired data
		      UtilitiesLibrary.measure("mapping operation "+mappingOperation.getName()+" -->\n", startTime, endTime, 0);

		      // directly showing the acquired data
		      System.out.println(mappingOperation.getName()+"  "+mappingOperation.getBody().getStartPosition()+" executed for "+(endTime - startTime) / 1e9);

		
		    }
		  }
}
