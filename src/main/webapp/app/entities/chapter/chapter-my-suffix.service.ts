import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ChapterMySuffix } from './chapter-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ChapterMySuffixService {

    private resourceUrl = 'api/chapters';

    constructor(private http: Http) { }

    create(chapter: ChapterMySuffix): Observable<ChapterMySuffix> {
        const copy = this.convert(chapter);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(chapter: ChapterMySuffix): Observable<ChapterMySuffix> {
        const copy = this.convert(chapter);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ChapterMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(chapter: ChapterMySuffix): ChapterMySuffix {
        const copy: ChapterMySuffix = Object.assign({}, chapter);
        return copy;
    }
}
