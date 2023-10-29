/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author Kyle
 */
public class SA2_Group3 extends javax.swing.JFrame {

    private Timer timer;
    private int selectedTime;
    Calendar calendar;
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    private long timeRemaining;
    String time = timeFormat.format(Calendar.getInstance().getTime());
    String tasksText = "";
    private boolean isWorkTime = true;

    public SA2_Group3() {
        initComponents();
        clock();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    timeRemaining -= 1000;
                    updateTimerLabel();
                } else {
                    timer.stop();
                    if (isWorkTime) {
                        JOptionPane.showMessageDialog(null, "Congratulations on being productive, take a break! You deserve it!");
                        System.out.println("Congratulations on being productive, take a break! You deserve it!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Rest time is over, let's get back to it!");
                        System.out.println("Rest time is over, let's get back to it!");
                    }
                    isWorkTime = !isWorkTime;
                }
            }
        });

        JMenuItem timersMenuItem = jMenu1.add("Timer");
        JMenuItem resetMenuItem = jMenu1.add("Reset");
        JMenuItem aboutTimerMenuItem = jMenu2.add("About the Pomodoro Timer");
        JMenuItem aboutUsMenuItem = jMenu2.add("About Us");

        timersMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SA2_Group3.this, "Timers menu clicked! You can add your timers functionality here.");
            }
        });
        
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPomodoro();
            }
        });
        
        aboutTimerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SA2_Group3.this, "This timer was created as a way for people to make use of their time efficiently. \nThe Pomodoro Method is a well-known method which combines a few short bursts of producitivity along with rests.");
            }
        });


        aboutUsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(1, 5));
                final JLabel label = new JLabel("Hover over me");

                String[] imagePaths = {
                    "laq.png",
                    "mag.png",
                    "mal.png",
                    "mam.png",
                    "pan.png"
                };

                String[] tooltips = {
                    "Laqueo, Lorenzo Miguel",
                    "Magno, Kristian Clarence",
                    "Mallari, Rafael",
                    "Mampusti, Rigel Kent",
                    "Panganiban, Kyle Dexter"
                };

                JLabel[] imageLabels = new JLabel[5];  // Create an array to store the labels

                for (int i = 0; i < 5; i++) {
                    ImageIcon icon = new ImageIcon(imagePaths[i]);
                    imageLabels[i] = new JLabel(icon);
                    imageLabels[i].setToolTipText(tooltips[i]);

                    imageLabels[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            label.setText(((JLabel) e.getSource()).getToolTipText()); // Get the tooltip from the label
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            label.setText("Hover over me");
                        }
                    });

                    panel.add(imageLabels[i]);
                }

                JOptionPane.showMessageDialog(SA2_Group3.this, panel, "About Us", JOptionPane.PLAIN_MESSAGE);
            }
        });




    }

    public void clock() {
        Thread clock = new Thread() {
            public void run() {
                while (true) {
                    String time = timeFormat.format(Calendar.getInstance().getTime());
                    currentTimeTitle.setText("Current Time: " + time);
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SA2_Group3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        clock.start();

    }
    
    private void resetPomodoro() {
    timer.stop();
    StartPomBtn.setEnabled(true);
    StopPomBtn.setEnabled(false);
    timeRemTitle.setText("Pomodoro stopped");
    updateTimerLabel();
    taskListTextArea.setText("");
    addTaskTextField.setText("");
    timeRemTitle.setText("Time Remaining: ");
    eftTitle.setText("Estimated Finish Time: ");
    }

    private void stopPomodoro() {
        timer.stop();
        timeRemTitle.setText("Pomodoro stopped");
        updateTimerLabel();
    }

    private void startPomodoro() {
        int timeInMilliseconds;
        if (selectedTime == 0) {
            timeInMilliseconds = 25 * 60 * 1000;
        } else {
            timeInMilliseconds = 5 * 60 * 1000;
        }

        timeRemaining = timeInMilliseconds;
        updateTimerLabel();
        JOptionPane.showMessageDialog(null, "Pomodoro started! Time selected: " + modeComboBox.getSelectedItem());
        System.out.println("Pomodoro started! Time selected: " + modeComboBox.getSelectedItem());
        timer.start();
    }

    private void updateTimerLabel() {
        int minutes = (int) (timeRemaining / 60000);
        int seconds = (int) ((timeRemaining % 60000) / 1000);
        timeRemTitle.setText("Time remaining: " + minutes + " min " + seconds + " sec");
        if (timer.isRunning()) {
            updateClockLabel();
        }
    }

    private void updateClockLabel() {
        long currentTimeMillis = System.currentTimeMillis();
        long estimatedFinishTimeMillis = currentTimeMillis + timeRemaining;
        Date estimatedFinishTime = new Date(estimatedFinishTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");
        String estimatedFinishTimeString = dateFormat.format(estimatedFinishTime);
        eftTitle.setText("Estimated Finish Time: " + estimatedFinishTimeString);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        modePanel = new javax.swing.JPanel();
        selectMode = new javax.swing.JLabel();
        modeComboBox = new javax.swing.JComboBox<>();
        StartPomBtn = new javax.swing.JButton();
        StopPomBtn = new javax.swing.JButton();
        StopPomBtn1 = new javax.swing.JButton();
        timePanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taskListTextArea = new javax.swing.JTextArea();
        tasListTitle = new javax.swing.JLabel();
        TimePanel = new javax.swing.JPanel();
        timeRemTitle = new javax.swing.JLabel();
        currentTimeTitle = new javax.swing.JLabel();
        eftTitle = new javax.swing.JLabel();
        titlePomodoro = new javax.swing.JLabel();
        tasksPanel = new javax.swing.JPanel();
        addTaskBtn = new javax.swing.JButton();
        clearAllBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        addTaskTextField = new javax.swing.JTextField();
        removeFirstTaskBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 153, 153));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        modePanel.setBackground(new java.awt.Color(255, 204, 204));
        modePanel.setMaximumSize(new java.awt.Dimension(200, 33));
        modePanel.setMinimumSize(new java.awt.Dimension(200, 33));

        selectMode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        selectMode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectMode.setText("Select Mode:");
        selectMode.setPreferredSize(new java.awt.Dimension(175, 14));

        modeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Work Time (25 mins)", "Rest Time (5 mins)" }));
        modeComboBox.setMaximumSize(new java.awt.Dimension(56, 20));
        modeComboBox.setPreferredSize(new java.awt.Dimension(175, 20));
        modeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeComboBoxActionPerformed(evt);
            }
        });

        StartPomBtn.setText("Start Pomodoro");
        StartPomBtn.setPreferredSize(new java.awt.Dimension(175, 30));
        StartPomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartPomBtnActionPerformed(evt);
            }
        });

        StopPomBtn.setEnabled(false);
        StopPomBtn.setText("Stop Pomodoro");
        StopPomBtn.setPreferredSize(new java.awt.Dimension(175, 30));
        StopPomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopPomBtnActionPerformed(evt);
            }
        });

        StopPomBtn.setEnabled(false);
        StopPomBtn1.setText("Pause/Resume");
        StopPomBtn1.setPreferredSize(new java.awt.Dimension(175, 30));
        StopPomBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopPomBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout modePanelLayout = new javax.swing.GroupLayout(modePanel);
        modePanel.setLayout(modePanelLayout);
        modePanelLayout.setHorizontalGroup(
            modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modePanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StartPomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StopPomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StopPomBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        modePanelLayout.setVerticalGroup(
            modePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modePanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(selectMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(StartPomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StopPomBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StopPomBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(modePanel);

        timePanel.setBackground(new java.awt.Color(255, 204, 204));
        timePanel.setForeground(new java.awt.Color(255, 204, 204));
        timePanel.setMaximumSize(new java.awt.Dimension(200, 33));
        timePanel.setMinimumSize(new java.awt.Dimension(200, 33));

        jPanel4.setLayout(new java.awt.BorderLayout());

        taskListTextArea.setColumns(20);
        taskListTextArea.setRows(5);
        taskListTextArea.setEditable(false);
        jScrollPane2.setViewportView(taskListTextArea);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tasListTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        tasListTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tasListTitle.setText("Task List");

        TimePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        TimePanel.setLayout(new java.awt.GridLayout(3, 0, 0, 10));

        timeRemTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeRemTitle.setText("Time Remaining:");
        TimePanel.add(timeRemTitle);

        currentTimeTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        currentTimeTitle.setText("Current Time: ");
        TimePanel.add(currentTimeTitle);

        eftTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        eftTitle.setText("Estimated Finish Time: ");
        TimePanel.add(eftTitle);

        titlePomodoro.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titlePomodoro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titlePomodoro.setText("Pomodoro Timer");

        javax.swing.GroupLayout timePanelLayout = new javax.swing.GroupLayout(timePanel);
        timePanel.setLayout(timePanelLayout);
        timePanelLayout.setHorizontalGroup(
            timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tasListTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TimePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(titlePomodoro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );
        timePanelLayout.setVerticalGroup(
            timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlePomodoro)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasListTitle)
                .addGap(18, 18, 18)
                .addComponent(TimePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        getContentPane().add(timePanel);

        tasksPanel.setBackground(new java.awt.Color(255, 204, 204));
        tasksPanel.setForeground(new java.awt.Color(255, 204, 204));
        tasksPanel.setMaximumSize(new java.awt.Dimension(200, 33));
        tasksPanel.setMinimumSize(new java.awt.Dimension(200, 33));

        addTaskBtn.setText("Add Task");
        addTaskBtn.setPreferredSize(new java.awt.Dimension(174, 30));
        addTaskBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTaskBtnActionPerformed(evt);
            }
        });

        clearAllBtn.setText("Clear All Tasks");
        clearAllBtn.setPreferredSize(new java.awt.Dimension(174, 30));
        clearAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Tasks Here:");
        jLabel1.setPreferredSize(new java.awt.Dimension(175, 14));

        removeFirstTaskBtn.setText("Clear First Task");
        removeFirstTaskBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFirstTaskBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tasksPanelLayout = new javax.swing.GroupLayout(tasksPanel);
        tasksPanel.setLayout(tasksPanelLayout);
        tasksPanelLayout.setHorizontalGroup(
            tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tasksPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addTaskBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearAllBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(addTaskTextField)
                    .addComponent(removeFirstTaskBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        tasksPanelLayout.setVerticalGroup(
            tasksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tasksPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(addTaskTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addTaskBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeFirstTaskBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearAllBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );

        getContentPane().add(tasksPanel);

        jMenu1.setText("Timer");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("About");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void StartPomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartPomBtnActionPerformed
        /*if (modeComboBox.getSelectedItem()=="Select Mode..."){
            JOptionPane.showMessageDialog(null,"You Did Not Select a Mode!","Error",JOptionPane.ERROR_MESSAGE);
        
        }else{
        JOptionPane.showMessageDialog(null,"Pomodoro started! Time selected: " + modeComboBox.getSelectedItem());
        System.out.println("Pomodoro started! Time selected: " + modeComboBox.getSelectedItem());
        }*/
        
        StartPomBtn.setEnabled(false);
        StopPomBtn.setEnabled(true);
        selectedTime = modeComboBox.getSelectedIndex();
        startPomodoro();
        
        

    }//GEN-LAST:event_StartPomBtnActionPerformed

    private void modeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modeComboBoxActionPerformed

    private void addTaskBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTaskBtnActionPerformed
        taskListTextArea.selectAll();
        taskListTextArea.replaceSelection("");
        String textLang = addTaskTextField.getText()+"\n";
        tasksText=tasksText+textLang;
        taskListTextArea.setText(tasksText);
        addTaskTextField.setText("");
        
        
    }//GEN-LAST:event_addTaskBtnActionPerformed

    private void clearAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBtnActionPerformed
        taskListTextArea.selectAll();
        taskListTextArea.replaceSelection("");
        
        tasksText="";
    }//GEN-LAST:event_clearAllBtnActionPerformed

    private void StopPomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopPomBtnActionPerformed
        StartPomBtn.setEnabled(true);
        StopPomBtn.setEnabled(false);
        stopPomodoro();
    }//GEN-LAST:event_StopPomBtnActionPerformed

    private void removeFirstTaskBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFirstTaskBtnActionPerformed
        String tasks = taskListTextArea.getText();
        int indexOfNewLine = tasks.indexOf("\n");
        if (indexOfNewLine != -1) {
        tasks = tasks.substring(indexOfNewLine + 1);
            taskListTextArea.setText(tasks);
    }
    }//GEN-LAST:event_removeFirstTaskBtnActionPerformed
    
    private boolean isPaused = false; // A boolean to track whether the Pomodoro is paused
    private void StopPomBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopPomBtn1ActionPerformed
        // TODO add your handling code here:
        if (!timer.isRunning()) { // If the timer is not running, resume the Pomodoro
        timer.start();
        isPaused = false;
        updateTimerLabel();
    } else { // If the timer is running, pause the Pomodoro
        timer.stop();
        isPaused = true;
        timeRemTitle.setText("Pomodoro paused");
    }
    // Toggle the text on the button
    StopPomBtn1.setText(isPaused ? "Resume Pomodoro" : "Pause Pomodoro");
    }//GEN-LAST:event_StopPomBtn1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SA2_Group3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SA2_Group3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SA2_Group3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SA2_Group3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new SA2_Group3().setVisible(true);
                

            }
        });
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton StartPomBtn;
    private javax.swing.JButton StopPomBtn;
    private javax.swing.JButton StopPomBtn1;
    private javax.swing.JPanel TimePanel;
    private javax.swing.JButton addTaskBtn;
    private javax.swing.JTextField addTaskTextField;
    private javax.swing.JButton clearAllBtn;
    private javax.swing.JLabel currentTimeTitle;
    private javax.swing.JLabel eftTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> modeComboBox;
    private javax.swing.JPanel modePanel;
    private javax.swing.JButton removeFirstTaskBtn;
    private javax.swing.JLabel selectMode;
    private javax.swing.JLabel tasListTitle;
    private javax.swing.JTextArea taskListTextArea;
    private javax.swing.JPanel tasksPanel;
    private javax.swing.JPanel timePanel;
    private javax.swing.JLabel timeRemTitle;
    private javax.swing.JLabel titlePomodoro;
    // End of variables declaration//GEN-END:variables
}