# Append this patch to the kernel build
# Adds the vxlan as module for k3s flannel
FILESEXTRAPATHS_prepend := "${THISDIR}:"
SRC_URI_append += "file://vxlan.cfg"