/*//////////////////////////////////////////////////////////////////////
	This file is part of FTP, an client FTP.
	Copyright (C) 2013  Nicolas Barranger <wicowyn@gmail.com>

    FTP is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FTP is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with FTP.  If not, see <http://www.gnu.org/licenses/>.
*///////////////////////////////////////////////////////////////////////

package Display;

import java.util.List;

import FTP.FTPFile;
import FTP.PiFTP;

public class FileExplorerFTP extends FileExplorer {
	private static final long serialVersionUID = 5350246563844990461L;
	private PiFTP pi=null;

	public FileExplorerFTP() {
	
	}

	@Override
	public void setPath(String path) {
		this.path=path;
		this.model.clear();
		if(this.pi==null) return;
		
		List<FTPFile> files=this.pi.getFiles(path);
		if(!this.path.isEmpty() && !this.path.equals("/")) model.addElement("..");
		for(FTPFile file : files){
			this.model.addElement(file.getName());
		}
		
		updateUI();
	}
	
	public void setPiFTP(PiFTP pi){
		this.pi=pi;
		this.model.clear();
	}
	
	public PiFTP getPiFTP(){
		return this.pi;
	}

	@Override
	protected void selected(int index) {
		String path=this.path + Constants.FILE_SEPARATOR + this.model.get(index);
		FTPFile file=this.pi.getFile(path);

		
		if(file.isDirectory()){
			setPath(file.getAbsPath());
		}
		else notifySelectedFile(path);
		
	}

	@Override
	protected void delete(int index) {
		String name=this.model.get(index);
		
		if(!name.equals("..")){
			this.pi.delete(this.pi.getFile(this.path + Constants.FILE_SEPARATOR + this.model.get(index)));
			setPath(this.path);
		}
		
	}

	@Override
	protected void move(int index, String newAbsPath) {
		String name=this.model.get(index);
		
		if(!name.equals("..")){
			this.pi.move(this.pi.getFile(this.path + Constants.FILE_SEPARATOR + this.model.get(index)), newAbsPath);
			setPath(this.path);
		}
		
	}

	@Override
	protected void info(int index) {
		FTPFile file=this.pi.getFile(this.path + Constants.FILE_SEPARATOR + this.model.get(index));
		ShowInfo inf=new ShowInfo();
		
		inf.setDir(file.getPath());
		inf.setName(file.getName());
		inf.setTypeFile(file.getType());
		inf.setSize(file.size());
		inf.setDate(file.getDate());
		inf.setPerm(file.getPerm());
		inf.setUnixOwner(Integer.toString(file.getUnixOwner()));
		inf.setUnixGroup(Integer.toString(file.getUnixGroup()));
		
		inf.setVisible(true);
		inf.setLocationRelativeTo(this);
	}

}
