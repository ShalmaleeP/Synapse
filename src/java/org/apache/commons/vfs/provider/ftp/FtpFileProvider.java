/*
 * Copyright 2002, 2003,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs.provider.ftp;

import org.apache.commons.vfs.FileName;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.provider.AbstractOriginatingFileProvider;
import org.apache.commons.vfs.provider.GenericFileName;

/**
 * A provider for FTP file systems.
 *
 * @author <a href="mailto:adammurdoch@apache.org">Adam Murdoch</a>
 * @version $Revision: 1.5 $ $Date: 2004/05/03 19:48:48 $
 */
public final class FtpFileProvider
    extends AbstractOriginatingFileProvider
{
    public final static String ATTR_FILE_ENTRY_PARSER = "FEP";
    private static final int DEFAULT_PORT = 21;

    public FtpFileProvider(FileSystemManager manager)
    {
        super(manager);
    }

    /**
     * Parses a URI.
     */
    protected FileName parseUri(final String uri)
        throws FileSystemException
    {
        return GenericFileName.parseUri(uri, DEFAULT_PORT);
    }

    /**
     * Creates the filesystem.
     */
    protected FileSystem doCreateFileSystem(final FileName name, final FileSystemOptions fileSystemOptions)
        throws FileSystemException
    {
        // Create the file system
        final GenericFileName rootName = (GenericFileName) name;
        return new FtpFileSystem(getFileSystemManager(), rootName, fileSystemOptions);
    }
}
