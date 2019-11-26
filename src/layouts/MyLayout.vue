<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn flat dense round @click="leftDrawerOpen = !leftDrawerOpen" icon="menu" aria-label="Menu" />

        <q-toolbar-title>Quasar App</q-toolbar-title>

        <div>Quasar v{{ $q.version }}</div>
      </q-toolbar>
    </q-header>

    <q-page-container>
      <q-page class="flex flex-center">
        <q-btn @click="takePhoto()">take photo</q-btn>
        <img alt="Quasar logo" :src="photoPath" />
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script>
import { Capacitor, Plugins, CameraResultType, FilesystemDirectory } from '@capacitor/core'
const { Camera, Filesystem } = Plugins

export default {
  name: 'MyLayout',
  data() {
    return {
      leftDrawerOpen: false,
      photoPath: ''
    }
  },
  methods: {
    async takePhoto() {
      const options = {
        resultType: CameraResultType.Uri
      }

      const originalPhoto = await Camera.getPhoto(options)
      const photoInTempStorage = await Filesystem.readFile({
        path: originalPhoto.path
      })

      let date = new Date(),
        time = date.getTime(),
        fileName = time + '.jpeg'

      await Filesystem.writeFile({
        data: photoInTempStorage.data,
        path: fileName,
        directory: FilesystemDirectory.Data
      })

      const finalPhotoUri = await Filesystem.getUri({
        directory: FilesystemDirectory.Data,
        path: fileName
      })

      this.photoPath = Capacitor.convertFileSrc(finalPhotoUri.uri)
      console.log(this.photoPath)
      // Capacitor.convertFileSrc(finalPhotoUri.uri)
    }
  }
}
</script>
