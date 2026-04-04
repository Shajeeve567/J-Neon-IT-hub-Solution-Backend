from pydantic import BaseModel, Field
from enum import Enum
from datetime import datetime

class DocumentInfo(BaseModel):
    id: int
    filename: str
    upload_timestamp: datetime

class DeleteFileRequest(BaseModel):
    file_id: int