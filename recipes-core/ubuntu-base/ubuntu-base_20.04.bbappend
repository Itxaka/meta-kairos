# Add packages to ubuntu rootfs, this are apt-get packages
UBUN_DEPENDENCES += " \
    sudo \
    parted \
    gdisk \
    rsync \
"

# Add the task between the configure and the install
addtask do_fix_sudo after do_ubuntu_install before do_install

# simple task to fix sudo permissions
do_fix_sudo() {
    bbwarn "Fix sudo"
    sudo chown root:root ${TMP_WKDIR}/usr/bin/sudo
    sudo chmod a=rx,u+ws ${TMP_WKDIR}/usr/bin/sudo
}