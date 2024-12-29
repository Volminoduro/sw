using MyApp.Models.Enums;

namespace RuneManager.Views
{
    partial class RuneView
    {
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.DataGridView dataGridViewRunes;
        private System.Windows.Forms.Button btnImport;
        private System.Windows.Forms.Button btnEditRune;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.dataGridViewRunes = new System.Windows.Forms.DataGridView();
            this.btnImport = new System.Windows.Forms.Button();
            this.btnEditRune = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewRunes)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewRunes
            // 
            this.dataGridViewRunes.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewRunes.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "Set",
                    HeaderText = "Set",
                    DataSource = Enum.GetValues(typeof(RuneSetType))
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "Slot",
                    HeaderText = "Slot",
                    DataSource = Enum.GetValues(typeof(RuneSlot))
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "Rarity",
                    HeaderText = "Rarity",
                    DataSource = Enum.GetValues(typeof(RuneRarity))
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "MainStat",
                    HeaderText = "Main Stat",
                    DataSource = Enum.GetValues(typeof(RuneMainStat))
                },
                new System.Windows.Forms.DataGridViewTextBoxColumn
                {
                    DataPropertyName = "MainStatValue",
                    HeaderText = "Main Stat Value"
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "SubStat1",
                    HeaderText = "Sub Stat 1",
                    DataSource = Enum.GetValues(typeof(RuneMainStat))
                },
                new System.Windows.Forms.DataGridViewTextBoxColumn
                {
                    DataPropertyName = "SubStat1Value",
                    HeaderText = "Sub Stat 1 Value"
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "SubStat2",
                    HeaderText = "Sub Stat 2",
                    DataSource = Enum.GetValues(typeof(RuneMainStat))
                },
                new System.Windows.Forms.DataGridViewTextBoxColumn
                {
                    DataPropertyName = "SubStat2Value",
                    HeaderText = "Sub Stat 2 Value"
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "SubStat3",
                    HeaderText = "Sub Stat 3",
                    DataSource = Enum.GetValues(typeof(RuneMainStat))
                },
                new System.Windows.Forms.DataGridViewTextBoxColumn
                {
                    DataPropertyName = "SubStat3Value",
                    HeaderText = "Sub Stat 3 Value"
                },
                new System.Windows.Forms.DataGridViewComboBoxColumn
                {
                    DataPropertyName = "SubStat4",
                    HeaderText = "Sub Stat 4",
                    DataSource = Enum.GetValues(typeof(RuneMainStat))
                },
                new System.Windows.Forms.DataGridViewTextBoxColumn
                {
                    DataPropertyName = "SubStat4Value",
                    HeaderText = "Sub Stat 4 Value"
                }
            });
            this.dataGridViewRunes.Location = new System.Drawing.Point(12, 12);
            this.dataGridViewRunes.Name = "dataGridViewRunes";
            this.dataGridViewRunes.Size = new System.Drawing.Size(1000, 368);
            this.dataGridViewRunes.TabIndex = 0;
            this.dataGridViewRunes.CellEndEdit += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewRunes_CellEndEdit);
            // 
            // btnImport
            // 
            this.btnImport.Location = new System.Drawing.Point(12, 386);
            this.btnImport.Name = "btnImport";
            this.btnImport.Size = new System.Drawing.Size(75, 23);
            this.btnImport.TabIndex = 1;
            this.btnImport.Text = "Import SWEX file";
            this.btnImport.UseVisualStyleBackColor = true;
            this.btnImport.Click += new System.EventHandler(this.btnImport_Click);
            // 
            // btnEditRune
            // 
            this.btnEditRune.Location = new System.Drawing.Point(937, 386);
            this.btnEditRune.Name = "btnEditRune";
            this.btnEditRune.Size = new System.Drawing.Size(75, 23);
            this.btnEditRune.TabIndex = 2;
            this.btnEditRune.Text = "Edit Rune";
            this.btnEditRune.UseVisualStyleBackColor = true;
            this.btnEditRune.Click += new System.EventHandler(this.btnEditRune_Click);
            // 
            // RuneView
            // 
            this.ClientSize = new System.Drawing.Size(1024, 450);
            this.Controls.Add(this.btnEditRune);
            this.Controls.Add(this.btnImport);
            this.Controls.Add(this.dataGridViewRunes);
            this.Name = "RuneView";
            this.Text = "Runes";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewRunes)).EndInit();
            this.ResumeLayout(false);

        }
    }
}
