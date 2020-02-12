package com.uni.de.qvto.uml2rdb.handler;

import org.eclipse.core.commands.AbstractHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.m2m.qvt.oml.util.Log;
import org.eclipse.m2m.qvt.oml.util.WriterLog;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

public class UML2RDBHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Refer to an existing transformation via URI
		URI transformationURI = URI.createFileURI("/Users/vijayshree/QVTo_BB_04_07_2019/com.uni.de.qvto.uml2rdb/Simpleuml_To_Rdb.qvto");
		// create executor for the given transformation
		TransformationExecutor executor = new TransformationExecutor(transformationURI);
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		
		// define the transformation input
		// Remark: we take the objects from a resource, however
		// a list of arbitrary in-memory EObjects may be passed
		ExecutionContextImpl context = new ExecutionContextImpl();
		
		ResourceSet resourceSet = new ResourceSetImpl();
		EPackage rdbMM = (EPackage) resourceSet.getResource(URI.createURI("file:////Users/vijayshree/QVTo_BB_04_07_2019/com.uni.de.qvto.uml2rdb/rdb.ecore"), true).getContents().get(0);
		EPackage simpleuml = (EPackage) resourceSet.getResource(URI.createURI("file:////Users/vijayshree/QVTo_BB_04_07_2019/com.uni.de.qvto.uml2rdb/SimpleUML.ecore"), true).getContents().get(0);
        EPackage.Registry.INSTANCE.put(rdbMM.getNsURI(), rdbMM);
        EPackage.Registry.INSTANCE.put(simpleuml.getNsURI(), simpleuml);

		Resource inResource = resourceSet.getResource(
				URI.createURI("file:////Users/vijayshree/QVTo_BB_04_07_2019/com.uni.de.qvto.uml2rdb/pim.simpleuml"), true);		
		EList<EObject> inObjects = inResource.getContents();

		// create the input extent with its initial contents
		ModelExtent input = new BasicModelExtent(inObjects);		
		// create an empty extent to catch the output
		ModelExtent output = new BasicModelExtent();

		// setup the execution environment details -> 
		// configuration properties, logger, monitor object etc.
		
		context.setConfigProperty("keepModeling", true);

		// run the transformation assigned to the executor with the given 
		// input and output and execution context -> ChangeTheWorld(in, out)
		// Remark: variable arguments count is supported
		ExecutionDiagnostic result = executor.execute(context, input, output);

		// check the result for success
		if(result.getSeverity() == Diagnostic.OK) {
			// the output objects got captured in the output extent
			List<EObject> outObjects = output.getContents();
			// let's persist them using a resource 
			Resource outResource = resourceSet.createResource(
					URI.createURI("file:////Users/vijayshree/QVTo_BB_04_07_2019/com.uni.de.qvto.uml2rdb/out.rdb"));
			outResource.getContents().addAll(outObjects);
			try {
				outResource.save(Collections.emptyMap());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// turn the result diagnostic into status and send it to error log			
			IStatus status = BasicDiagnostic.toIStatus(result);
			
		}
		return null;
	}
}
