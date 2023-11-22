# Install packages for kairos, this are yocto packages
CORE_IMAGE_BASE_INSTALL += " \
			kairos-agent \
			kairos-configs \
			k3s \
			provider-kairos \
"
ROOTFS_POSTPROCESS_COMMAND += "do_fixes; "

# Fix sudo perms under ubuntu as they are missing the setuid bit
do_fixes() {
    bbwarn "Fix sudo"
    chown -R root:root ${IMAGE_ROOTFS}/usr/bin/sudo
    bbwarn "Adding setuid bit"
    chmod a+s ${IMAGE_ROOTFS}/usr/bin/sudo
    bbwarn "Sudo fixed"
}