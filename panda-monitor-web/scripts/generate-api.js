import http from 'node:http'
import fs from 'node:fs'
import tempDir from 'temp-dir'

import AdmZip from 'adm-zip'

import { v4 as uuid } from 'uuid'

import fse from 'fs-extra'

const sourceUrl = 'http://localhost:8080/ts.zip'
const tmpFilePath = `${tempDir}/${uuid()}.zip`
const generatePath = 'src/__generated'

console.log(`Downloading ${sourceUrl}...`)

const tmpFile = fs.createWriteStream(tmpFilePath)

const request = http.get(sourceUrl, (response) => {
  response.pipe(tmpFile)
  tmpFile.on('finish', () => {
    tmpFile.close()
    console.log('File save success: ', tmpFilePath)

    // Remove generatePath if it exists
    if (fs.existsSync(generatePath)) {
      console.log('Removing existing generatePath...')
      fse.removeSync(generatePath)
      console.log('Existing generatePath removed.')
    }

    // Unzip the file using adm-zip
    console.log('Unzipping the file...')
    const zip = new AdmZip(tmpFilePath, {})
    zip.extractAllTo(generatePath, true)
    console.log('File unzipped successfully.')

    // Remove the temporary file
    console.log('Removing temporary file...')
    fs.unlink(tmpFilePath, (err) => {
      if (err) {
        console.error('Error while removing temporary file:', err)
      }
      else {
        console.log('Temporary file removed.')
      }
    })
  })
})

request.on('error', (err) => {
  console.error('Error while downloading file:', err)
})
