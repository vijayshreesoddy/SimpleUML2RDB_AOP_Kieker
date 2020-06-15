package com.uni.de.qvto.aspect;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin
{
  public static final String PLUGIN_ID = "com.uni.de.qvto.aspect";
  private static BundleContext context;

  public static BundleContext getContext()
  {
    return context;
  }

  @Override
  public void start(final BundleContext context) throws Exception
  {
    Activator.context = context;
  }

  @Override
  public void stop(final BundleContext context) throws Exception
  {
    Activator.context = null;
  }

}
