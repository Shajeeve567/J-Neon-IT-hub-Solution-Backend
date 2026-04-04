from fastapi import FastAPI, UploadFile, HTTPException, File
from fastapi.middleware.cors import CORSMiddleware
import os
import uuid
import shutil
from db_utils import insert_document_record, get_all_documents
from models import DocumentInfo
from typing import List
import logging
from chroma_utils import index_document_to_chroma

logging.basicConfig(filename='app.log', level=logging.INFO)

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], # In production, replace with specific origins
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/documents", response_model=List[DocumentInfo])
def fetch_documents():
    documents = get_all_documents()
    return documents

@app.post("/upload-doc")
def upload_document(file: UploadFile = File(...)):
    allowed_extensions = ['.pdf']
    file_extension = os.path.splitext(file.filename)[1].lower()

    if file_extension not in allowed_extensions:
        raise HTTPException(status_code=400, detail=f"Unsupported file type. Allowed types are: {', '.join(allowed_extensions)}")
    
    unique_filename =  f"{uuid.uuid4()}_{file.filename}"
    temp_file_path = os.path.join(os.getcwd(), unique_filename)

    try:
        # Save the uploaded file to a temporary file
        with open(temp_file_path, "wb") as buffer:
            shutil.copyfileobj(file.file, buffer)

        file_id = insert_document_record(file.filename)
        success = index_document_to_chroma(temp_file_path, file_id)
        
        if success:
            return {"message": f"File {file.filename} has been successfully uploaded and indexed.", "file_id": file_id}

        
    finally:
        if os.path.exists(temp_file_path):
            os.remove(temp_file_path)