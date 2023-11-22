# Install packages for kairos, this are yocto packages
CORE_IMAGE_BASE_INSTALL += " \
			kairos-agent \
			kairos-configs \
			k3s \
			provider-kairos \
"
ROOTFS_POSTINSTALL_COMMAND += "do_fixes; "

# Fix sudo perms under ubuntu as they are missing the setuid bit
do_fixes() {
    bbwarn "Fix sudo"
    sudo chown -R root:root ${IMAGE_ROOTFS}/usr/bin/sudo
    sudo chmod a+s ${IMAGE_ROOTFS}/usr/bin/sudo
    bbwarn "Remove motd"
    sudo chmod -x ${IMAGE_ROOTFS}/etc/update-motd.d/*

}