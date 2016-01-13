package LANG_PROJECT_ID.ide.ui;

import melnorme.lang.ide.ui.LangUIPlugin;

import org.osgi.framework.BundleContext;

public class LANGUAGE_UIPlugin extends LangUIPlugin {
	
	@Override
	protected LANGUAGE_OperationsConsoleUIHandler createOperationsConsoleListener() {
		return new LANGUAGE_OperationsConsoleUIHandler();
	}
	
	@Override
	protected void doCustomStop(BundleContext context) {
	}
	
}