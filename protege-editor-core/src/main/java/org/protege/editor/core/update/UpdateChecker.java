package org.protege.editor.core.update;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URL;
import java.util.Optional;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 25-Aug-2006<br><br>

 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class UpdateChecker {

    private final URL url;

    private final Optional<PluginInfo> b;


    public UpdateChecker(URL updateFileURL, Optional<PluginInfo> pluginDescriptor) {
        this.url = checkNotNull(updateFileURL);
        this.b = checkNotNull(pluginDescriptor);
    }


    public Optional<PluginInfo> run() throws PluginDocumentParseException {
        final PluginInfoDocumentParser pluginInfoDocumentParser = new PluginInfoDocumentParser(url);
        PluginInfo info = pluginInfoDocumentParser.parseDocument(b);
        if(!b.isPresent()) {
            info.setPluginInfo(null);
            return Optional.of(info);
        }
        if (info.getAvailableVersion().compareTo(b.get().getAvailableVersion()) > 0) {
            // New version available!
            info.setPluginInfo(b.get());
            return Optional.of(info);
        }
        return Optional.empty();
    }
}
