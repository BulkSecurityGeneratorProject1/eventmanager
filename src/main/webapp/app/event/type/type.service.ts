import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { EventType } from './type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EventTypeService {

    private resourceUrl = SERVER_API_URL + 'api/event-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/event-types';

    constructor(private http: Http) { }

    create(eventType: EventType): Observable<EventType> {
        const copy = this.convert(eventType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(eventType: EventType): Observable<EventType> {
        const copy = this.convert(eventType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EventType> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to EventType.
     */
    private convertItemFromServer(json: any): EventType {
        const entity: EventType = Object.assign(new EventType(), json);
        return entity;
    }

    /**
     * Convert a EventType to a JSON which can be sent to the server.
     */
    private convert(eventType: EventType): EventType {
        const copy: EventType = Object.assign({}, eventType);
        return copy;
    }
}
