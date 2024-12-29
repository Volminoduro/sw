using RuneManager.Controllers;
using RuneManager.Data;
using RuneManager.Views;

namespace RuneManager
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            // Initialize DataContext
            IDataContext dataContext = new DataContext();

            // Initialize controllers
            RuneController runeController = new RuneController(dataContext);
            FilterController filterController = new FilterController(dataContext);

            // Create views
            RuneView runeView = new RuneView(runeController, dataContext);
            FilterView filterView = new FilterView(filterController);

            // Set the positions of the forms
            runeView.StartPosition = FormStartPosition.Manual;
            filterView.StartPosition = FormStartPosition.Manual;
            runeView.Location = new System.Drawing.Point(0, 0);
            filterView.Location = new System.Drawing.Point(runeView.Width, 0);

            // Register FormClosed event for both forms to exit the application when both are closed
            runeView.FormClosed += (s, e) => CheckExitApplication(runeView, filterView);
            filterView.FormClosed += (s, e) => CheckExitApplication(runeView, filterView);

            // Show the forms
            runeView.Show();
            filterView.Show();

            // Start the application with one of the forms as the main form
            Application.Run(runeView);
        }

        static void CheckExitApplication(Form runeView, Form filterView)
        {
            if (!runeView.Visible && !filterView.Visible)
            {
                Application.ExitThread();
            }
        }
    }
}
