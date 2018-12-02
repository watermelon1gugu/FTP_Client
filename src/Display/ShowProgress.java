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

import javax.swing.*;

import FTP.TransferTask;
import FTP.TransferTaskListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowProgress extends JPanel {
	private static final long serialVersionUID = -5585045453142379373L;

	public ShowProgress(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void addTransferTask(TransferTask task){
		JProgressBar bar=new JProgressBar();
		//////////////////////////change///////////////////////
		JButton Button=new JButton("Stop");
		bar.setMaximum((int) task.getSize());
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(bar);
		add(Button);



		Button.addMouseListener(new MouseListener() {
			boolean flag = false;
			@Override
			public void mouseClicked(MouseEvent e) {
				flag =  !flag;
				if (flag){
					Button.setText("continue");
					//////////////进程停止////////////
					////////////////addAction//////////////
				}else {
					Button.setText("stop");
					//////////////进程开始////////////
					////////////////addAction//////////////
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});

		ListenTask listener=new ListenTask();
		listener.bar=bar;
		listener.Button = Button;
		task.addListener(listener);


		////////////////////////////////////////////////////////
		updateUI();
	}
	
	private class ListenTask implements TransferTaskListener{
		public JProgressBar bar;
		JButton Button;
		@Override
		public void transfered(long transfered) {
			this.bar.setValue((int) transfered);
			
		}

		@Override
		public void finish() {
			ShowProgress.this.remove(this.bar);
			ShowProgress.this.remove(this.Button);
			this.bar=null;
			ShowProgress.this.updateUI();
			
		}
		
	}
}
