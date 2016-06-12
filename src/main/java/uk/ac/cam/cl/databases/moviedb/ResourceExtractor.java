package uk.ac.cam.cl.databases.moviedb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import net.sf.corn.cps.CPResourceFilter;
import net.sf.corn.cps.CPScanner;
import net.sf.corn.cps.RootedURL;

/**
 * Searches for resources that have been bundled into a jar file on the classpath,
 * and extracts them to a regular directory on the filesystem. This allows us to
 * distribute a dataset as part of a jar file, and still allow it to be read by
 * a database storage engines (which expects a directory of files that it can
 * access).
 */
public class ResourceExtractor {
    private final String resourcePath;
    private final File outputDir;

    public ResourceExtractor(String resourcePath, File outputDir) {
        this.resourcePath = resourcePath;
        this.outputDir = outputDir;
    }

    public void extract() {
        System.err.println("Extracting database files, please wait (we only need to do this once)...");
        try {
            outputDir.mkdirs();
            for (URL resource : CPScanner.scanResources(new ResourcePathFilter())) {
                String urlStr = resource.toString();
                String filename = urlStr.substring(urlStr.lastIndexOf(resourcePath) + resourcePath.length());
                Path outputPath = new File(outputDir, filename).toPath();
                try (InputStream input = resource.openStream()) {
                    Files.copy(input, outputPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.err.println("Done.");
    }

    private class ResourcePathFilter implements CPResourceFilter {
        @Override
        public boolean filterable(Object subject) {
            return (subject instanceof RootedURL);
        }

        @Override
        public boolean accept(Object subject) {
            if (subject instanceof RootedURL) {
                return ((RootedURL) subject).getResourceURL().toString().contains(resourcePath);
            } else {
                return true;
            }
        }
    }
}
