package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class AIFinder
{
    private static final String JAVA_FILE_EXTENSION = ".java";
    
    private final File aiDirectory_;
    
    
    public AIFinder(final String directory)
    {
        Validate.notNull(directory);
        try
        {
            aiDirectory_ = new File(directory);
        } catch (Exception e)
        {
            throw new IllegalArgumentException(String.format(
                    "%s is an invalid location for AIs", directory));
        }
    }
    
    private Collection<File> recursivelyFindAllFiles(final File currentDirectory)
    {
        Validate.isTrue(currentDirectory.isDirectory(),
                String.format("%s is not a directory!", currentDirectory));

        final File [] filesInDirectory = currentDirectory.listFiles();
        final Collection<File> filesInCurrentDirectory = new ArrayList<File>();
        for (final File file : filesInDirectory)
        {
            if (file.isDirectory())
            {
                filesInCurrentDirectory.addAll(recursivelyFindAllFiles(file));
            } else
            {
                filesInCurrentDirectory.add(file);
            }
        }

        return filesInCurrentDirectory;
    }
    
    public Map<String, Class<?>> findAIs()
    {
        final Map<String, Class<?>> aiNameToClass = new HashMap<String, Class<?>>();

        final Collection<File> filesInDirectory = recursivelyFindAllFiles(aiDirectory_);
        for (final File file : filesInDirectory)
        {
            final String fullFileName = file.getName();
            if (!fullFileName.endsWith(JAVA_FILE_EXTENSION))
            {
                continue;
            }

            String className = fullFileName.substring(0, fullFileName.length()
                    - JAVA_FILE_EXTENSION.length());
            {
                File currentFile = file;
                do
                {
                    currentFile = currentFile.getParentFile();
                    String currentDirectoryName = currentFile.getName();
                    className = currentDirectoryName + "." + className;
                } while (!aiDirectory_.equals(currentFile));
            }

            try
            {
                final Class<?> instance = Class.forName(className);
                aiNameToClass.put(className, instance);
            } catch (Exception e)
            {
                // TODO: LOG
            }
        }
        return aiNameToClass;
    }
}
