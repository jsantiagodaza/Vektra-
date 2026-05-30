from pathlib import Path
import re
path = Path(r'c:\Users\santi\OneDrive\Documents\NetBeansProjects\Vektra-\src\vektra\View\LoginFrame.java')
text = path.read_text(encoding='utf-8', errors='replace')
text, n = re.subn(r'configurarPlaceholder\(contrasenatxt,.*?\);', 'configurarPlaceholder(contrasenatxt, "Ingresa tu Contraseña");', text, flags=re.S)
print('placeholder replacements:', n)
helper = '''
    private boolean esAdministrador() {
        return cmbTipoUsuario.getSelectedItem() != null
            && cmbTipoUsuario.getSelectedItem().toString().equals("Administrador");
    }

    private boolean esAdminValido() {
        String email = logincorreotxt.getText().trim();
        String password = contrasenatxt.getText().trim();
        return email.equals("admin@admin.com") && password.equals("adminadmin");
    }

    private void abrirMainFrame() {
        MainFrameView main = new MainFrameView();
        main.setLocationRelativeTo(null);
        main.setVisible(true);
        this.dispose();
    }
'''
if 'private boolean esAdministrador()' not in text:
    target = '    private static class SimpleDocListener implements javax.swing.event.DocumentListener {'
    text = text.replace(target, helper + target)
    print('Inserted helper methods')
path.write_text(text, encoding='utf-8')
