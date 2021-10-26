/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.tools;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Cursor;

import com.archimatetool.editor.ui.IArchiImages;
import com.archimatetool.editor.ui.ImageFactory;

/**
 * Extended ConnectionCreationToolEntry so we can provide our own cursors
 * The image masking of cursors on Linux is broken, see https://bugs.eclipse.org/bugs/show_bug.cgi?id=575425
 * Also, we need 2x cursors for Windows 200% scale
 * 
 * @author Phillip Beauvoir
 */
public class ExtConnectionCreationToolEntry extends ConnectionCreationToolEntry {
    
    public ExtConnectionCreationToolEntry(String label, String shortDesc, CreationFactory factory, ImageDescriptor iconSmall, ImageDescriptor iconLarge) {
        super(label, shortDesc, factory, iconSmall, iconLarge);
        setToolClass(ExtConnectionCreationToolEntryTool.class);
    }
    
    public static class ExtConnectionCreationToolEntryTool extends ConnectionCreationTool {
        public ExtConnectionCreationToolEntryTool() {
            setDefaultCursor(null);
            setDisabledCursor(null);
        }
        
        @Override
        public void deactivate() {
            super.deactivate();
            doCursors();
        }
        
        @Override
        public void activate() {
            doCursors();
            super.activate();
        }
        
        private void doCursors() {
            // Set this first because super.getDisabledCursor() will return super.getDefaultCursor() if null
            if(getDisabledCursor() == null) {
                setDisabledCursor(createCursor(IArchiImages.CURSOR_IMG_ADD_NOT_CONNECTION));
            }
            else {
                getDisabledCursor().dispose();
                setDisabledCursor(null);
            }
            
            if(getDefaultCursor() == null) {
                setDefaultCursor(createCursor(IArchiImages.CURSOR_IMG_ADD_CONNECTION));
            }
            else {
                getDefaultCursor().dispose();
                setDefaultCursor(null);
            }
        }
        
        private Cursor createCursor(String imageName) {
            return new Cursor(
                    null,
                    IArchiImages.ImageFactory.getImageDescriptor(imageName).getImageData(ImageFactory.getLogicalDeviceZoom()),
                    0,
                    0);
        }
    }

}