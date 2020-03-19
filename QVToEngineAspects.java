package com.uni.de.qvto.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.m2m.internal.qvt.oml.expressions.MappingOperation;
import org.eclipse.m2m.qvt.oml.examples.blackbox.UtilitiesLibrary;

import com.uni.de.qvto.uml2rdb.handler.UML2RDBHandler;


@Aspect
public class QVToEngineAspects {
	@Around("call(public void com.uni.de.qvto.uml2rdb.handler.UML2RDBHandler.printUML2RDB(..)) " +
		      "&& args(obj)")
		  public void aroundPrint(final String obj, final ProceedingJoinPoint joinPoint) throws Throwable
		  {
		 System.out.println("hitting aspect");
		    long nanoTime1 = System.nanoTime();

		    Exception ex = null;
		    try
		    {
		      joinPoint.proceed();
		    }
		    catch (Exception e)
		    {
		      ex = e;
		      throw e;
		    }
		    finally
		    {
		      long nanoTime2 = System.nanoTime();

		   
		      System.out.println(obj+" is "+(nanoTime2 - nanoTime1) / 1e9);

		
		    }
		  }
	
	/*@Around("call(Object org.eclipse.core.commands.IHandler.execute(..)) " +
   "&& args(event)")
	  public Object aroundPrint(final ExecutionEvent event, final ProceedingJoinPoint joinPoint) throws Throwable
{
System.out.println("hitting aspect");
  long nanoTime1 = System.nanoTime();

  Exception ex = null;
  try
  {
    joinPoint.proceed();
  }
  catch (Exception e)
  {
    ex = e;
    throw e;
  }
  finally
  {
    long nanoTime2 = System.nanoTime();

 
    System.out.println(event.getTrigger()+" is "+(nanoTime2 - nanoTime1) / 1e9);


  }
  return null;
}*/
	
	@Around("call(public java.lang.Object org.eclipse.m2m.internal.qvt.oml.expressions.util.QVTOperationalVisitor.visitMappingOperation(..)) " +
		      "&& args(mappingOperation)")
		  public Object aroundCheckInConfiguration(final MappingOperation mappingOperation,final ProceedingJoinPoint joinPoint) throws Throwable
		  {
		    long startTime = UtilitiesLibrary.currentTime();

		    Exception ex = null;
		    try
		    {
		      return (Object) joinPoint.proceed();
		    }
		    catch (Exception e)
		    {
		      ex = e;
		      throw e;
		    }
		    finally
		    {
		      long endTime = UtilitiesLibrary.currentTime();
		      
		      UtilitiesLibrary.measure(mappingOperation.getName(), startTime, endTime, 0);

		   
		      //System.out.println(mappingOperation.getName()+"  "+mappingOperation.getBody().getStartPosition()+" is "+(endTime - startTime) / 1e9);

		
		    }
		  }
}
