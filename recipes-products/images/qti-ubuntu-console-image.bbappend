# Install packages for kairos, this are yocto packages
CORE_IMAGE_BASE_INSTALL += " \
			kairos-agent \
			kairos-configs \
			k3s \
"
ROOTFS_POSTPROCESS_COMMAND += "do_fix_sudo; "

# Fix sudo perms under ubuntu as they are missing the setuid bit
do_fix_sudo(){
    bbwarn "Fix sudo"
    sudo chown -R root:root ${IMAGE_ROOTFS}/usr/bin/sudo
    sudo chmod a+s ${IMAGE_ROOTFS}/usr/bin/sudo
}
