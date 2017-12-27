/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.net.URLDecoder;
import java.security.CodeSource;

public class LocationFinder
{
	private static String jarLocation = null;

	public static String getJarLocation(Class aclass)
	{

		if (jarLocation != null)
		{
			return jarLocation;
		}

		try
		{
			CodeSource codeSource = aclass.getProtectionDomain().getCodeSource();

			File jarFile;

			if (codeSource.getLocation() != null)
			{
				jarFile = new File(codeSource.getLocation().toURI());
			} else
			{
				String path = aclass.getResource(aclass.getSimpleName() + ".class").getPath();
				String jarFilePath = path.substring(path.indexOf(":") + 1, path.indexOf("!"));
				jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
				jarFile = new File(jarFilePath);
			}
			jarLocation = jarFile.getParentFile().getAbsolutePath();
			return jarLocation;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ".";
	}

}
