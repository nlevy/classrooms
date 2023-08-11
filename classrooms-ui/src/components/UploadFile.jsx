import React, { useState } from 'react';
import axios from 'axios';
import * as XLSX from 'xlsx';

const UploadFile = ({uploadUrl}) => {
    const [file, setFile] = useState(null);
    const [uploading, setUploading] = useState(false);

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        setFile(selectedFile);
    };

    const handleUpload = async () => {
        if (!file) {
            return;
        }

        try {
            setUploading(true);

            const reader = new FileReader();
            reader.onload = (event) => {
                const data = new Uint8Array(event.target.result);
                const workbook = XLSX.read(data, { type: 'array' });
                const jsonData = XLSX.utils.sheet_to_json(workbook.Sheets[workbook.SheetNames[0]]);

                // Sending JSON data as POST request
                axios.post(uploadUrl, jsonData)
                    .then(response => {
                        console.log('Upload successful:', response);
                        setUploading(false);
                    })
                    .catch(error => {
                        console.error('Error uploading:', error);
                        setUploading(false);
                    });
            };
            reader.readAsArrayBuffer(file);
        } catch (error) {
            console.error('Error processing file:', error);
            setUploading(false);
        }
    };

    return (
        <div>
            <h2>Upload Excel File</h2>
            <input type="file" accept=".xlsx" onChange={handleFileChange} />
            <button onClick={handleUpload} disabled={uploading || !file}>
                {uploading ? 'Uploading...' : 'Upload'}
            </button>
        </div>
    );
};

export default UploadFile;
