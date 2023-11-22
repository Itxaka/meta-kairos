DESCRIPTION = "Kairos provider"
SRC_URI = "https://github.com/kairos-io/provider-kairos/releases/download/v2.6.0-rc2/provider-kairos_2.6.0-rc2_linux_arm64.tar.gz"
# No idea how the license checksum works, this just disables it completely
LICENSE = "CLOSED"
SRC_URI[sha256sum] = "7a6a034d22330ad18fae845fb48046264b8cc02fe38906318aade7bbe8999915"

FILES_${PN} += "/system/providers/agent-provider-kairos /usr/bin/kairos /usr/bin/kairosctl"

# Remove warning about binary being stripped, we know.
INSANE_SKIP_${PN} += "already-stripped"

do_install () {
    install -d ${D}/system/providers/
    install -d ${D}/usr/bin/
    # Needed for k3s, it stores the config there?
    install -d ${D}/etc/sysconfig
    install -m 0755 ${WORKDIR}/kairos-cli ${D}/system/providers/agent-provider-kairos
    install -m 0755 ${WORKDIR}/kairosctl ${D}/usr/bin/
    # This is also done by the luet package
    ln -s /system/providers/agent-provider-kairos ${D}/usr/bin/kairos
}



