/*******************************************************************************
 * Copyright (c) 2016 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package melnorme.lang.ide.ui.editor.hover;

import static melnorme.utilbox.core.Assert.AssertNamespace.assertNotNull;

import java.util.Optional;

import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.texteditor.ITextEditor;

import melnorme.lang.ide.ui.text.DocDisplayInfoSupplier;
import melnorme.lang.ide.ui.text.DocumentationHoverCreator;
import melnorme.lang.tooling.common.ISourceBuffer;

/**
 * Standard documentation hover.
 * (used in editor hovers extensions, and editor information provider (F2))
 */
public abstract class AbstractDocHover implements ILangEditorTextHover<String> {
	
	protected final DocumentationHoverCreator hoverCreator = new DocumentationHoverCreator(); 
	
	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return hoverCreator.getHoverControlCreator();
	}
	
	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return hoverCreator.getInformationPresenterControlCreator();
	}
	
	protected boolean requiresSavedBuffer() {
		return true;
	}
	
	@Override
	public String getHoverInfo(ISourceBuffer sourceBuffer, IRegion hoverRegion, Optional<ITextEditor> editor,
			ITextViewer textViewer, boolean allowedToSaveBuffer) {
		assertNotNull(sourceBuffer);
		
		if(requiresSavedBuffer() && sourceBuffer.isDirty()) {
			
			if(!allowedToSaveBuffer) {
				return null; // Nothing we can do, quite
			}
			
			boolean success = sourceBuffer.trySaveBuffer();
			if(!success) {
				return null;
			}
			
		}
		
		return getHoverInfo(sourceBuffer, hoverRegion, textViewer);
	}
	
	@Override
	public String getHoverInfo(ISourceBuffer sourceBuffer, IRegion hoverRegion, ITextViewer textViewer) {
		return new DocDisplayInfoSupplier(sourceBuffer, hoverRegion.getOffset()).get();
	}
	
}